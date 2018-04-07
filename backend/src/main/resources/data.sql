
-- Ambitos o permisos del usuario en las ordenes de pedido
insert into scopes(name,nameSpring,createdAt,createdBY) values ('Crear ordenes de pedido','ROLE_CREATE_ORDERS',now(),'SYSTEM');
insert into scopes(name,nameSpring,createdAt,createdBY) values ('Ver ordenes de pedido','ROLE_READ_ORDERS',now(),'SYSTEM');
insert into scopes(name,nameSpring,createdAt,createdBY) values ('Modificar ordenes de pedido','ROLE_UPDATE_ORDERS',now(),'SYSTEM');
insert into scopes(name,nameSpring,createdAt,createdBY) values ('Eliminar ordenes de pedido','ROLE_DELETE_ORDERS',now(),'SYSTEM');
-- TODO Ambitos o permisos del usuario para gestionar su perfil
-- USUARIO DEMO (El password es 123456)
insert into users(fullName,userName,password,phone,createdAt,createdBY,enabled,avatar) values (
    'Marco Silverio Castro','marcosilv7486@gmail.com','$2a$11$9Ylt0Uyz3T6ghHCiWP4mlOD2Q0AhuBxx156kRt8k8M4pRabbl2/X2',
    '942696030',now(),'SYSTEM',true,'');
insert into users(fullName,userName,password,phone,createdAt,createdBY,enabled,avatar) values (
    'Soy el Usuario Bloqueado','usuariobloqueado@gmail.com','$2a$11$9Ylt0Uyz3T6ghHCiWP4mlOD2Q0AhuBxx156kRt8k8M4pRabbl2/X2',
    '942696030',now(),'SYSTEM',false,'');
-- Relacios scopes(permisos o ambitos) entre users(usuarios)
insert into user_scopes(user_id,scope_id,createdAt,createdBY) VALUE (1,1,now(),'SYSTEM');
insert into user_scopes(user_id,scope_id,createdAt,createdBY) VALUE (1,2,now(),'SYSTEM');
insert into user_scopes(user_id,scope_id,createdAt,createdBY) VALUE (1,3,now(),'SYSTEM');
insert into user_scopes(user_id,scope_id,createdAt,createdBY) VALUE (1,4,now(),'SYSTEM');