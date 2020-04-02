package com.cimb.tcj.filter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import com.cimb.tcj.entity.UserAccess;
import com.cimb.tcj.repository.UserAccessRepository;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class AuthorizationFilter extends ZuulFilter {

	private static final Set<HttpMethod> ALLOW_METHODS = new HashSet<>(Arrays.asList(
			(HttpMethod[]) new HttpMethod[] { HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE }));
	@Autowired
	private UserAccessRepository userAccessRepository;

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	public static PathContainer toPathContainer(String path) {
		if (path == null) {
			return null;
		}
		return PathContainer.parsePath(path);
	}

	@Override
	public Object run() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final RequestContext requestContext = RequestContext.getCurrentContext();

		HttpServletRequest request = requestContext.getRequest();
		RequestContext ctx = RequestContext.getCurrentContext();

		String path = request.getServletPath();
		System.out.println("uri: " + path + "| method: " + request.getMethod());
		if (isAccessible(auth.getName(), path, request.getMethod())) {
			return null;
		} else {
			ctx.setSendZuulResponse(false);
			ctx.setResponseBody("Route not allow");
			ctx.getResponse().setHeader("Content-Type", "text/plain;charset=UTF-8");
			ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
			return null;
		}

	}
	
	public static void main(String[] args) {
		PathPatternParser pp = new PathPatternParser();
		PathPattern pathPattern = pp.parse("/dummy/*/a");
		PathContainer pathContainer = toPathContainer("/dummy/createAccount");
		System.out.println(pathPattern.matches(pathContainer));
	}

	private boolean isAccessible(@NotBlank String userName, @NotBlank String path, @NotBlank String httpMethod) {
		List<UserAccess> accessList = userAccessRepository.findByUsername(userName);
		PathPatternParser pp = new PathPatternParser();
		PathContainer pathContainer = toPathContainer(path);
		for (UserAccess userAccess : accessList) {
			String uri = userAccess.getResourceUri();
			PathPattern pathPattern = pp.parse(uri);
			if (pathPattern.matches(pathContainer) && isMethodAllow(userAccess, httpMethod)) {
				return true;
			}
		}
		return false;
	}

	private boolean isMethodAllow(final UserAccess userAccess, final String requestHTTPMethod) {
		Map<String, Boolean> accessMap = populateAccessMap(userAccess);
		HttpMethod httpMethod = HttpMethod.resolve(requestHTTPMethod);

		if (httpMethod == null || !ALLOW_METHODS.contains(httpMethod)) {
			return false;
		}
		return accessMap.get(requestHTTPMethod.toUpperCase());
	}

	private Map<String, Boolean> populateAccessMap(final UserAccess userAccess) {

		Map<String, Boolean> accessMap = new HashMap<>();

		accessMap.put(HttpMethod.DELETE.name(), userAccess.isDeleteAllow());
		accessMap.put(HttpMethod.POST.name(), userAccess.isPostAllow());
		accessMap.put(HttpMethod.PUT.name(), userAccess.isPutAllow());
		accessMap.put(HttpMethod.GET.name(), userAccess.isGetAllow());

		return accessMap;
	}
}