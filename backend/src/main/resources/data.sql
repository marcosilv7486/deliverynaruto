
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

-- Data maestra de familias
insert into product_family(name,createdAt,createdBY) VALUE ('Comidas',now(),'SYSTEM');
insert into product_family(name,createdAt,createdBY) VALUE ('Postres',now(),'SYSTEM');
insert into product_family(name,createdAt,createdBY) VALUE ('Bebidas',now(),'SYSTEM');
insert into product_family(name,createdAt,createdBY) VALUE ('Makis',now(),'SYSTEM');
insert into product_family(name,createdAt,createdBY) VALUE ('Aperitivos',now(),'SYSTEM');
-- Data Maestra para las subfamilias
insert into product_subfamily(name,createdAt,createdBY,family_id) values ('Entradas',now(),'SYSTEM',5);
insert into product_subfamily(name,createdAt,createdBY,family_id) values ('Agemono',now(),'SYSTEM',1);
insert into product_subfamily(name,createdAt,createdBY,family_id) values ('Donburimono',now(),'SYSTEM',1);
insert into product_subfamily(name,createdAt,createdBY,family_id) values ('Yakimono',now(),'SYSTEM',1);
insert into product_subfamily(name,createdAt,createdBY,family_id) values ('Itamemono',now(),'SYSTEM',1);
insert into product_subfamily(name,createdAt,createdBY,family_id) values ('Nimono',now(),'SYSTEM',1);
insert into product_subfamily(name,createdAt,createdBY,family_id) values ('Menrui Guarnic',now(),'SYSTEM',1);
insert into product_subfamily(name,createdAt,createdBY,family_id) values ('Guarnición',now(),'SYSTEM',1);
insert into product_subfamily(name,createdAt,createdBY,family_id) values ('Postres',now(),'SYSTEM',2);
insert into product_subfamily(name,createdAt,createdBY,family_id) values ('Calientes',now(),'SYSTEM',3);
insert into product_subfamily(name,createdAt,createdBY,family_id) values ('Gaseosas',now(),'SYSTEM',3);
insert into product_subfamily(name,createdAt,createdBY,family_id) values ('Aguas',now(),'SYSTEM',3);
insert into product_subfamily(name,createdAt,createdBY,family_id) values ('Cervezas',now(),'SYSTEM',3);
insert into product_subfamily(name,createdAt,createdBY,family_id) values ('Sake',now(),'SYSTEM',3);
insert into product_subfamily(name,createdAt,createdBY,family_id) values ('Entrada',now(),'SYSTEM',4);
insert into product_subfamily(name,createdAt,createdBY,family_id) values ('PlatoFrio',now(),'SYSTEM',5);
-- DataMaestra para los productos
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Yakitori',1,15,'http://www.menu-tokyo.jp/tradition/img/yakitori.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Gyoza',1,15,'http://utero.pe/wp-content/uploads/2016/08/naruto4.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Tori no Karaage',1,15,'https://www.espaiwabisabi.com/wp-content/uploads/2015/05/2015-02-07-14.09.39-1024x576.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Chicken Katsu',2,24,'https://upload.wikimedia.org/wikipedia/commons/thumb/6/61/Torikatsu.jpg/220px-Torikatsu.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Tonkatsu',2,24,'https://3.bp.blogspot.com/-lMNeywmISHI/VMPORaCIYhI/AAAAAAAAEPc/hIjSI33bEF8/s1600/5105767239_1dc8af7fa7_b.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Naruto Chicken',2,24,'https://upload.wikimedia.org/wikipedia/commons/thumb/6/61/Torikatsu.jpg/220px-Torikatsu.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Oyakodon',3,22,'http://www.seriouseats.com/recipes/images/2016/08/20160802-oyakodon-4-1500x1125.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Katsudon',3,23,'https://upload.wikimedia.org/wikipedia/commons/thumb/4/45/Katsudon_01.jpg/800px-Katsudon_01.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Chicken Teriyaki',4,24,'http://rasamalaysia.com/wp-content/uploads/2016/03/chicken-teriyaki4.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Buta no Shogayaki',4,24,'https://2.bp.blogspot.com/-YXi9nmn_xBw/Uv0teO8KSLI/AAAAAAAAAfU/vM3sdvICl-I/s1600/Buta_no_Shogayaki2.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Yakimeshi',4,24,'https://cdn.kiwilimon.com/ss_secreto/140/thumb400x300-p_143.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Yasaiitame',5,23,'http://www.nekotabi.es/wp-content/uploads/2011/04/20080428yusaishinnikuitame.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Kare Raisu',6,22,'http://2.bp.blogspot.com/_Uw2tP1MDeU4/RtMDn5rd8fI/AAAAAAAAAQI/RSfq-7t_Bmg/s400/curry0.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Katsu KarePollo',6,25,'https://4.bp.blogspot.com/-RqloJZZ-6Cc/U6BhtjmY2bI/AAAAAAAADUY/BZQpL1k91Dk/s1600/tonkatsu_kare_raisu.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Shoyu Ramen',7,27,'https://1.bp.blogspot.com/-Yyl7pdIel58/VMUX_EMdPpI/AAAAAAAAEPs/xUpHzhwkpxc/s1600/shoyu_ramen2.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Miso Ramen',7,27,'https://www.justonecookbook.com/wp-content/uploads/2014/09/Miso-Ramen-II-600x900.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Tonkotsu Ramen',7,27,'https://3.bp.blogspot.com/-lMNeywmISHI/VMPORaCIYhI/AAAAAAAAEPc/hIjSI33bEF8/s1600/5105767239_1dc8af7fa7_b.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    '1/2 Shoyu Ramen',7,20,'http://staticpro-wpmu.atresmedia.com/wp-content/uploads/sites/2/2016/06/26203045/Captura34.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    '1/2 Miso Ramen',7,20,'https://www.justonecookbook.com/wp-content/uploads/2014/09/Miso-Ramen-II-600x900.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    '1/2 Tonkotsu Ramen',7,20,'https://3.bp.blogspot.com/-lMNeywmISHI/VMPORaCIYhI/AAAAAAAAEPc/hIjSI33bEF8/s1600/5105767239_1dc8af7fa7_b.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Chashu Extra',7,7,'http://www.seriouseats.com/recipes/images/2012/03/20120301-tonkotsu-chashu-cha-siu-pork-belly-ramen-08.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Menma Extra',7,2,'https://s3-media1.fl.yelpcdn.com/bphoto/JWZ6tnjE4skD_6frZy9K6g/o.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Gohan',8,6,'https://s3-media4.fl.yelpcdn.com/bphoto/cn9XHGZyWY3-voYs2Vy2Yg/o.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Ocha',10,3,'https://media.istockphoto.com/photos/gohan-japanese-food-picture-id536719867',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Té Inglés',10,3,'https://gastronomiaycia.republica.com/wp-content/photos/crocante_sesamo.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Anís',10,3,'http://3.bp.blogspot.com/_iXGxAuNdd0k/S8Z3G_1XhRI/AAAAAAAABYs/B2TDRMesto0/s1600/aniseseedtea.jpg',now(),'SYSTEM'
);insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Manzanilla',10,3,'https://mejorconsalud.com/wp-content/uploads/2013/12/Manzanilla-2-500x353.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Hierba Luisa',10,3,'http://boticarionatural.com/wp-content/uploads/2013/05/IMG_5174-230x180.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Mugicha',10,3,'http://boticarionatural.com/wp-content/uploads/2013/05/IMG_5174-230x180.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Coca Cola',11,5,'https://s3-us-west-1.amazonaws.com/img.coca-colafemsa.com/assets/images/es/carreras/1-2-3.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Coca Cola Zero',11,5,'http://www.lomejordelmercado.com/4272/pack-de-8-latas-de-coke-zero.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Inca Kola',11,5,'http://delivery.pappas.pe/wp-content/uploads/2014/04/pappas-pollos-brasa-parrillas-lima-bebidas-inca-kola.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Inca Kola Zero',11,5,'https://i.pinimg.com/originals/b0/49/b0/b049b09077ce73ef4ae41a2fb184a4d8.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Sprite',11,5,'http://www.coca-colaindia.com/content/dam/journey/in/en/private/Choices/related%20stories/India-sprite-fountain.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Fanta',11,5,'http://www.coca-colaindia.com/content/dam/journey/in/en/private/Choices/related%20stories/India-sprite-fountain.jpg',now(),'SYSTEM'
);
insert into products(name,subFamily_id,price,image,createdAt,createdBY) values (
    'Agua sin Gas',12,5,'https://i5.walmartimages.com/asr/646bffb9-c5f0-4c05-a918-cea2a6faeb44_1.9a516e8c58a0dfd6f24de8748ca0b2e2.jpeg',now(),'SYSTEM'
);
