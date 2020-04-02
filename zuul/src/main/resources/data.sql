DROP TABLE IF EXISTS user_access;
 
CREATE TABLE user_access (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  resource_uri varchar(2000) not null, 
  is_Post_Allow boolean default  false,
  is_Get_Allow boolean default  false,
  is_Put_Allow boolean default  false,
  is_Delete_Allow boolean default  false
);
 
INSERT INTO user_access (username,resource_uri,is_Post_Allow,is_Get_Allow,is_Put_Allow,is_Delete_Allow) VALUES
  ( 'seannfong', '/dummy/updateAccount/*', true, true, true, true),
  ( 'du', '/auth/profile', true, true, true, true),
  ( 'a', '/auth/address', true, true, true, true),
  ( 's', '/auth/social_media', true, true, true, true),
  ( 'seannfong', '/dummy/createAccount', true, true, true, true),
  ( 'seannfong', '/dummy/updateAccount/*', true, true, true, true);