INSERT INTO `sys_resource` VALUES ('1', '资源', 'menu', '', '0', '0/', '', '1');
INSERT INTO `sys_resource` VALUES ('82', '系统管理', 'menu', 'menus', '1', '0/1/', 'sys:*', '0');
INSERT INTO `sys_resource` VALUES ('83', '组织机构', 'menu', 'organization', '82', '0/1/82/', 'organization:*', '0');
INSERT INTO `sys_resource` VALUES ('84', '新增', 'button', '', '83', '0/1/82/83/', 'organization:create', '0');
INSERT INTO `sys_resource` VALUES ('85', '修改', 'button', '', '83', '0/1/82/83/', 'organization:update', '0');
INSERT INTO `sys_resource` VALUES ('86', '删除', 'button', '', '83', '0/1/82/83/', 'organization:delete', '0');
INSERT INTO `sys_resource` VALUES ('87', '查看', 'button', '', '83', '0/1/82/83/', 'organization:view', '0');
INSERT INTO `sys_resource` VALUES ('88', '应用管理', 'menu', 'app', '82', '0/1/82/', 'app:*', '0');
INSERT INTO `sys_resource` VALUES ('89', '新增', 'button', '', '88', '0/1/82/88/', 'app:create', '0');
INSERT INTO `sys_resource` VALUES ('90', '修改', 'button', '', '88', '0/1/82/88/', 'app:update', '0');
INSERT INTO `sys_resource` VALUES ('91', '删除', 'button', '', '88', '0/1/82/88/', 'app:delete', '0');
INSERT INTO `sys_resource` VALUES ('92', '查看', 'button', '', '88', '0/1/82/88/', 'app:view', '0');
INSERT INTO `sys_resource` VALUES ('93', '用户管理', 'menu', 'user', '82', '0/1/82/', 'user:*', '0');
INSERT INTO `sys_resource` VALUES ('94', '新增', 'button', '', '93', '0/1/82/93/', 'user:create', '0');
INSERT INTO `sys_resource` VALUES ('95', '修改', 'button', '', '93', '0/1/82/93/', 'user:update', '0');
INSERT INTO `sys_resource` VALUES ('96', '删除', 'button', '', '93', '0/1/82/93/', 'user:delete', '0');
INSERT INTO `sys_resource` VALUES ('97', '查看', 'button', '', '93', '0/1/82/93/', 'user:view', '0');
INSERT INTO `sys_resource` VALUES ('98', '菜单管理', 'menu', 'resource', '82', '0/1/82/', 'resource:*', '0');
INSERT INTO `sys_resource` VALUES ('99', '新增', 'button', '', '98', '0/1/82/98/', 'resource:create', '0');
INSERT INTO `sys_resource` VALUES ('100', '修改', 'button', '', '98', '0/1/82/98/', 'resource:update', '0');
INSERT INTO `sys_resource` VALUES ('101', '删除', 'button', '', '98', '0/1/82/98/', 'resource:delete', '0');
INSERT INTO `sys_resource` VALUES ('102', '查看', 'button', '', '98', '0/1/82/98/', 'resource:view', '0');
INSERT INTO `sys_resource` VALUES ('103', '角色管理', 'menu', 'role', '82', '0/1/82/', 'role:*', '0');
INSERT INTO `sys_resource` VALUES ('104', '新增', 'button', '', '103', '0/1/82/103/', 'role:create', '0');
INSERT INTO `sys_resource` VALUES ('105', '修改', 'button', '', '103', '0/1/82/103/', 'role:update', '0');
INSERT INTO `sys_resource` VALUES ('106', '删除', 'button', '', '103', '0/1/82/103/', 'role:delete', '0');
INSERT INTO `sys_resource` VALUES ('107', '查看', 'button', '', '103', '0/1/82/103/', 'role:view', '0');
INSERT INTO `sys_resource` VALUES ('108', '授权管理', 'menu', 'authorization', '82', '0/1/82/', 'authorization:*', '0');
INSERT INTO `sys_resource` VALUES ('109', '新增', 'button', '', '108', '0/1/82/108/', 'authorization:create', '0');
INSERT INTO `sys_resource` VALUES ('110', '修改', 'button', '', '108', '0/1/82/108/', 'authorization:update', '0');
INSERT INTO `sys_resource` VALUES ('111', '删除', 'button', '', '108', '0/1/82/108/', 'authorization:delete', '0');
INSERT INTO `sys_resource` VALUES ('112', '查看', 'button', '', '108', '0/1/82/108/', 'authorization:view', '0');
INSERT INTO `sys_resource` VALUES ('115', '百货后台管理', 'menu', '', '1', '0/1/', 'shopping:*', '0');
INSERT INTO `sys_resource` VALUES ('116', '医疗后台管理', 'menu', '', '1', '0/1/', 'hospital:*', '0');
INSERT INTO `sys_resource` VALUES ('117', '微信管理', 'menu', 'menus', '1', '0/1/', 'weixin:*', '0');
INSERT INTO `sys_resource` VALUES ('118', '基础信息', 'menu', 'wxcode/list', '117', '0/1/117/', 'weixin:mpinfo:*', '0');
INSERT INTO `sys_resource` VALUES ('119', '新增', 'button', '', '118', '0/1/117/118/', 'weixin:mpinfo:create', '0');
INSERT INTO `sys_resource` VALUES ('120', '修改', 'button', '', '118', '0/1/117/118/', 'weixin:mpinfo:update', '0');
INSERT INTO `sys_resource` VALUES ('121', '修改', 'button', '', '118', '0/1/117/118/', 'weixin:mpinfo:delete', '0');
INSERT INTO `sys_resource` VALUES ('122', '查看', 'button', '', '118', '0/1/117/118/', 'weixin:mpinfo:view', '0');
INSERT INTO `sys_resource` VALUES ('124', '首页', 'menu', '', '116', '0/1/116/', 'hospital:index:*', '0');
INSERT INTO `sys_resource` VALUES ('125', '会员中心', 'menu', '', '116', '0/1/116/', 'hospital:member:*', '0');
INSERT INTO `sys_resource` VALUES ('126', '商品管理', 'menu', '', '116', '0/1/116/', 'hospital:item:*', '0');
INSERT INTO `sys_resource` VALUES ('127', '订单中心', 'menu', '', '116', '0/1/116/', 'hospital:order:*', '0');
INSERT INTO `sys_resource` VALUES ('128', '卡券管理', 'menu', '', '116', '0/1/116/', 'hospital:card:*', '0');
INSERT INTO `sys_resource` VALUES ('129', '消息管理', 'menu', '', '117', '0/1/117/', 'weixin:message:*', '0');
INSERT INTO `sys_resource` VALUES ('130', '素材中心', 'menu', '', '117', '0/1/117/', 'weixin:material:*', '0');
INSERT INTO `sys_resource` VALUES ('131', '门店管理', 'menu', '', '116', '0/1/116/', 'hospital:store:*', '0');
INSERT INTO `sys_resource` VALUES ('132', '门店管理', 'menu', '', '131', '0/1/116/123/131/', '', '0');
INSERT INTO `sys_resource` VALUES ('133', '添加门店', 'menu', '', '131', '0/1/116/123/131/', '', '0');
INSERT INTO `sys_resource` VALUES ('135', '医师管理', 'menu', '', '116', '0/1/116/', 'hospital:doctor:*', '0');
INSERT INTO `sys_resource` VALUES ('136', '医师管理', 'menu', '', '135', '0/1/116/123/135/', 'hospital:doctor:view', '0');
INSERT INTO `sys_resource` VALUES ('146', '门店管理', 'menu', '', '115', '0/1/115/', 'shoping:mall:*', '0');
INSERT INTO `sys_resource` VALUES ('148', '门店信息管理', 'menu', '', '116', '0/1/116/', 'hospital:storeinfo:*', '0');
INSERT INTO `sys_resource` VALUES ('149', '门店信息管理', 'menu', '', '148', '0/1/116/123/148/', 'hospital:storeinfo:view', '0');
INSERT INTO `sys_resource` VALUES ('150', '首页', 'menu', '', '115', '0/1/115/', 'shopping:index:*', '0');
INSERT INTO `sys_resource` VALUES ('151', '商品管理', 'menu', '', '115', '0/1/115/', 'shopping:goods:*', '0');
INSERT INTO `sys_resource` VALUES ('152', '会员管理', 'menu', '', '115', '0/1/115/', 'shopping:member:*', '0');
INSERT INTO `sys_resource` VALUES ('153', '营销管理', 'menu', '', '115', '0/1/115/', 'shopping:marketing:*', '0');
INSERT INTO `sys_resource` VALUES ('156', '数据分析', 'menu', '', '115', '0/1/115/', 'shopping:statistics:*', '0');
INSERT INTO `sys_resource` VALUES ('157', '文章管理', 'menu', '', '115', '0/1/115/', 'shopping:article:*', '0');
INSERT INTO `sys_resource` VALUES ('158', '订单管理', 'menu', '', '115', '0/1/115/', 'shopping:order:*', '0');
INSERT INTO `sys_resource` VALUES ('159', '商品列表', 'menu', '', '151', '0/1/115/151/', 'shopping:goods:view:*', '0');
INSERT INTO `sys_resource` VALUES ('160', '商品分类', 'menu', '', '151', '0/1/115/151/', 'shopping:goods:cat:view:*', '0');
INSERT INTO `sys_resource` VALUES ('162', '订单列表', 'menu', '', '158', '0/1/115/158/', 'shopping:order:view:*', '0');
INSERT INTO `sys_resource` VALUES ('163', '退换货列表', 'menu', '', '158', '0/1/115/158/', 'shopping:order:return:view:*', '0');
INSERT INTO `sys_resource` VALUES ('164', '快递类型', 'menu', '', '158', '0/1/115/158/', 'shopping:order:express:view:*', '0');
INSERT INTO `sys_resource` VALUES ('166', '编辑门店', 'menu', '', '146', '0/1/115/146/', 'shopping:mall:update:*', '0');
INSERT INTO `sys_resource` VALUES ('167', '楼层列表', 'menu', '', '146', '0/1/115/146/', 'shopping:mall:floor:view:*', '0');
INSERT INTO `sys_resource` VALUES ('168', '支付配置', 'menu', '', '146', '0/1/115/146/', 'shopping:mall:pay:view:*', '0');


INSERT INTO `sys_role` VALUES ('1', 'superAdmin', '超级管理员', '82,83,88,93,98,103,108,117', '1');
INSERT INTO `sys_role` VALUES ('6', 'shoppingOrgAdmin', '百货集团管理员', '150,151,160', '0');
INSERT INTO `sys_role` VALUES ('7', 'shoppingMallAdmin', '百货门店管理员', '129,130,146,150,151,158,159,162,163,164,166,167,168', '0');
INSERT INTO `sys_role` VALUES ('8', 'hospitalOrgAdmin', '医疗集团管理员', '124,125,126,127,128,129,130,131,135', '0');
INSERT INTO `sys_role` VALUES ('9', 'hospitalMallAdmin', '医疗门店管理员', '127,135,136,148,149', '0');


INSERT INTO `sys_user` VALUES ('1', '1', 'admin', '20e3e4976998a8abafa3dd401c9e8a81', '3b83c03c477f11f21614388c7810045b', '0');
INSERT INTO `sys_user` VALUES ('2', '93', 'test12', '6acb792df7758f9a189ebf0057b3bba3', 'de7102be0b2932d1ebf13ae1e528e475', '0');
INSERT INTO `sys_user` VALUES ('3', '1', 't2', '143847dbbdcb6cb2227f0d6fff5571bf', 'ba80e225a6a0eb90912ade0f83c0fa30', '0');
INSERT INTO `sys_user` VALUES ('4', '1', 'aaa', '28da10ea3f674a7216862b898026be04', '59369bec1d0aee5d0dd0e723f99a618d', '0');
INSERT INTO `sys_user` VALUES ('5', '1', 'bbb', '143847dbbdcb6cb2227f0d6fff5571bf', 'ba80e225a6a0eb90912ade0f83c0fa30', '0');
INSERT INTO `sys_user` VALUES ('6', '1', 'ccc', '143847dbbdcb6cb2227f0d6fff5571bf', 'ba80e225a6a0eb90912ade0f83c0fa30', '0');
INSERT INTO `sys_user` VALUES ('7', '1', 'ddd', '143847dbbdcb6cb2227f0d6fff5571bf', 'ba80e225a6a0eb90912ade0f83c0fa30', '0');
INSERT INTO `sys_user` VALUES ('8', '1', 'eee', '143847dbbdcb6cb2227f0d6fff5571bf', 'ba80e225a6a0eb90912ade0f83c0fa30', '0');
INSERT INTO `sys_user` VALUES ('9', '1', 'fff', '143847dbbdcb6cb2227f0d6fff5571bf', 'ba80e225a6a0eb90912ade0f83c0fa30', '0');
INSERT INTO `sys_user` VALUES ('10', '1', 'ggg', '143847dbbdcb6cb2227f0d6fff5571bf', 'ba80e225a6a0eb90912ade0f83c0fa30', '0');
INSERT INTO `sys_user` VALUES ('11', '1', 'hhh', '143847dbbdcb6cb2227f0d6fff5571bf', 'ba80e225a6a0eb90912ade0f83c0fa30', '0');
INSERT INTO `sys_user` VALUES ('12', '1', 'jjj', '143847dbbdcb6cb2227f0d6fff5571bf', 'ba80e225a6a0eb90912ade0f83c0fa30', '0');
INSERT INTO `sys_user` VALUES ('13', '1', 'kkk', '143847dbbdcb6cb2227f0d6fff5571bf', 'ba80e225a6a0eb90912ade0f83c0fa30', '0');
INSERT INTO `sys_user` VALUES ('14', '1', 'lll', '143847dbbdcb6cb2227f0d6fff5571bf', 'ba80e225a6a0eb90912ade0f83c0fa30', '0');
INSERT INTO `sys_user` VALUES ('15', '1', 'mmm', '143847dbbdcb6cb2227f0d6fff5571bf', 'ba80e225a6a0eb90912ade0f83c0fa30', '0');
INSERT INTO `sys_user` VALUES ('16', '1', 'nnn', '143847dbbdcb6cb2227f0d6fff5571bf', 'ba80e225a6a0eb90912ade0f83c0fa30', '0');
INSERT INTO `sys_user` VALUES ('17', '1', '000', '143847dbbdcb6cb2227f0d6fff5571bf', 'ba80e225a6a0eb90912ade0f83c0fa30', '0');
INSERT INTO `sys_user` VALUES ('18', '1', 'ppp', '143847dbbdcb6cb2227f0d6fff5571bf', 'ba80e225a6a0eb90912ade0f83c0fa30', '0');
INSERT INTO `sys_user` VALUES ('57', '93', 'fgfg', 'f0a1e2850728fde20f8e4c7781a79ea1', 'a9f8799d92a3cb7e8d517c09e3e04087,tete,tata,tata', '0');
INSERT INTO `sys_user` VALUES ('58', '93', 'vnvbn', '2064063737a4b25bd435fa30397903f9', '3e2c1cfde07a0d68df104c46d62c7006', '0');
INSERT INTO `sys_user` VALUES ('59', '90', 'ewqweqwe', '123123', '', '0');
INSERT INTO `sys_user` VALUES ('60', '93', '123123', '314324324', '', '0');
INSERT INTO `sys_user` VALUES ('61', '93', 'cym', 'e23f8c734fc4b389d933e4e696c1a2a8', '27726b911ae3b20a9356b2fd6a150f15', '0');
INSERT INTO `sys_user` VALUES ('62', '93', 'cy', '928bea627c9ae4c123085b6847931f5f', '646c33bd5106ad1109b93acec712c0dc', '0');
INSERT INTO `sys_user` VALUES ('63', '90', '123', '2a33c75b20339bea00f5530cf1497ef6', '3294218b28778612e7ce75c20f67bb37', '0');
INSERT INTO `sys_user` VALUES ('64', '93', '1212', 'c33af3f3c07bcfdba6735745ea090511', '06d38b31c54d19d0880e03da8d6a96cc', '0');