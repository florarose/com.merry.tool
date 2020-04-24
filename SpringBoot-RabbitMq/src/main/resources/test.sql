
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'merry', '123456');
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `type` int(32) DEFAULT '0' COMMENT '0',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `name` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='商品';

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', '0', '2020-04-24 16:10:31', '2020-04-24 16:10:31', '手机', '精品手机');
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `type` int(11) NOT NULL COMMENT '日志类型:1登录 2登出',
  `description` varchar(255) DEFAULT NULL COMMENT '日志描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='登录日志表';

DROP TABLE IF EXISTS `msg_log`;
CREATE TABLE `msg_log` (
  `msg_id` varchar(255) NOT NULL DEFAULT '' COMMENT '消息唯一标识',
  `msg` text COMMENT '消息体, json格式化',
  `exchange` varchar(255) NOT NULL DEFAULT '' COMMENT '交换机',
  `routing_key` varchar(255) NOT NULL DEFAULT '' COMMENT '路由键',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态: 0投递中 1投递成功 2投递失败 3已消费',
  `try_count` int(11) NOT NULL DEFAULT '0' COMMENT '重试次数',
  `next_try_time` datetime DEFAULT NULL COMMENT '下一次重试时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`msg_id`),
  UNIQUE KEY `unq_msg_id` (`msg_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='消息投递日志';

-- ----------------------------
-- Records of msg_log
-- ----------------------------
INSERT INTO `msg_log` VALUES ('1e7e99c8efcc447ab188a3c7317c2f90', '{\"to\":\"460118025@qq.com\",\"title\":\"测试\",\"content\":\"回家测试\",\"msgId\":\"1e7e99c8efcc447ab188a3c7317c2f90\"}', 'mail.exchange', 'mail.routing.key', '2', '3', '2020-04-14 17:42:32', '2020-04-14 17:38:32', '2020-04-14 17:46:29');
INSERT INTO `msg_log` VALUES ('237df1e50f5b4c66b244883ca3e3ef4c', '{\"to\":\"460118025@qq.com\",\"title\":\"测试\",\"content\":\"回家测试\",\"msgId\":\"237df1e50f5b4c66b244883ca3e3ef4c\"}', 'mail.exchange', 'mail.routing.key', '3', '0', '2020-04-14 17:48:09', '2020-04-14 17:47:09', '2020-04-14 17:47:10');
INSERT INTO `msg_log` VALUES ('2edf840194b547c09c3edd3a150822b1', '{\"to\":\"460118025@qq.com\",\"title\":\"测试\",\"content\":\"回家测试\",\"msgId\":\"2edf840194b547c09c3edd3a150822b1\"}', 'mail.exchange', 'mail.routing.key', '3', '2', '2020-04-14 17:40:59', '2020-04-14 17:37:59', '2020-04-14 17:46:31');
INSERT INTO `msg_log` VALUES ('4deb6c3d253b465abda4c7c708fc4265', '{\"to\":\"15736797611@163.com\",\"title\":\"骚扰一下\",\"content\":\"想你了,你在做什么呀,诶呀，不理我，打你\",\"msgId\":\"4deb6c3d253b465abda4c7c708fc4265\"}', 'mail.exchange', 'mail.routing.key', '3', '0', '2020-04-14 18:21:40', '2020-04-14 18:20:40', '2020-04-14 18:20:41');
INSERT INTO `msg_log` VALUES ('586979ded7d749c4b0948dd858dd9fd8', '{\"to\":\"460118025@qq.com\",\"title\":\"测试\",\"content\":\"回家测试\",\"msgId\":\"586979ded7d749c4b0948dd858dd9fd8\"}', 'mail.exchange', 'mail.routing.key', '3', '0', '2020-04-14 17:47:18', '2020-04-14 17:46:18', '2020-04-14 17:46:21');
INSERT INTO `msg_log` VALUES ('8491875c24e849d4a91806a0a468393d', '{\"to\":\"15736797611@163.com\",\"title\":\"情书\",\"content\":\"何时何地我们也曾相恋，只是如今淡忘了曾经。或许再见面彼此会有那一丝心跳的不规律，但这难道不是灵魂深处对前世的依恋？我不明白为何你那不经意间的一个动作﹑一句话语会让我呆立桥头，但我明白你就是我所寻觅的人。为此我孤注一掷，倾付所有，只为伊人为我驻足，留下一株盛开在我心中的微笑。我所想要的并不多，只要你给我留下一个可以含语深情凝望你的角落。为自己留下一个一生都不悔的回忆。\\n\\n　　回忆是掌心里的水，从指缝间流走，带走的是掌心的温度，留下的却是无尽的思念。我不想你从我身边就这样走过，我要生命的余年都有你陪伴。\\n\\n　　我终于鼓起勇气告诉你，我喜欢你。我坦白，我不要冰冷快乐的回忆，我要你的体温，暖我一世的心晨。\\n\\n　　莫回首，回首需驻足，无怨无悔。\\n\\n　　既回首，风雨皆为尘，自始至终。\\n\\n　　与我一起摘下那只属于我们的四叶草，咫尺天涯，不离不弃。\",\"msgId\":\"8491875c24e849d4a91806a0a468393d\"}', 'mail.exchange', 'mail.routing.key', '3', '0', '2020-04-14 18:17:13', '2020-04-14 18:16:13', '2020-04-14 18:16:13');
INSERT INTO `msg_log` VALUES ('aafd78671d9245c590cfc83a31b08fa6', '{\"to\":\"460118025@qq.com\",\"title\":\"测试\",\"content\":\"回家测试\",\"msgId\":\"aafd78671d9245c590cfc83a31b08fa6\"}', 'mail.exchange', 'mail.routing.key', '3', '1', '2020-04-14 17:42:44', '2020-04-14 17:40:44', '2020-04-14 17:46:31');
INSERT INTO `msg_log` VALUES ('b5f40b5315cf45a79e26a1a3f789be96', '{\"to\":\"15736797611@163.com\",\"title\":\"情书\",\"content\":\"何时何地我们也曾相恋，只是如今淡忘了曾经。或许再见面彼此会有那一丝心跳的不规律，但这难道不是灵魂深处对前世的依恋？我不明白为何你那不经意间的一个动作﹑一句话语会让我呆立桥头，但我明白你就是我所寻觅的人。为此我孤注一掷，倾付所有，只为伊人为我驻足，留下一株盛开在我心中的微笑。我所想要的并不多，只要你给我留下一个可以含语深情凝望你的角落。为自己留下一个一生都不悔的回忆。\\n\\n　　回忆是掌心里的水，从指缝间流走，带走的是掌心的温度，留下的却是无尽的思念。我不想你从我身边就这样走过，我要生命的余年都有你陪伴。\\n\\n　　我终于鼓起勇气告诉你，我喜欢你。我坦白，我不要冰冷快乐的回忆，我要你的体温，暖我一世的心晨。\\n\\n　　莫回首，回首需驻足，无怨无悔。\\n\\n　　既回首，风雨皆为尘，自始至终。\\n\\n　　与我一起摘下那只属于我们的四叶草，咫尺天涯，不离不弃。\",\"msgId\":\"b5f40b5315cf45a79e26a1a3f789be96\"}', 'mail.exchange', 'mail.routing.key', '3', '0', '2020-04-14 18:17:14', '2020-04-14 18:16:14', '2020-04-14 18:16:14');
INSERT INTO `msg_log` VALUES ('d6e9ea65e9804f46b01378c18b7af79b', '{\"to\":\"15736797611@163.com\",\"title\":\"情书\",\"content\":\"何时何地我们也曾相恋，只是如今淡忘了曾经。或许再见面彼此会有那一丝心跳的不规律，但这难道不是灵魂深处对前世的依恋？我不明白为何你那不经意间的一个动作﹑一句话语会让我呆立桥头，但我明白你就是我所寻觅的人。为此我孤注一掷，倾付所有，只为伊人为我驻足，留下一株盛开在我心中的微笑。我所想要的并不多，只要你给我留下一个可以含语深情凝望你的角落。为自己留下一个一生都不悔的回忆。\\n\\n　　回忆是掌心里的水，从指缝间流走，带走的是掌心的温度，留下的却是无尽的思念。我不想你从我身边就这样走过，我要生命的余年都有你陪伴。\\n\\n　　我终于鼓起勇气告诉你，我喜欢你。我坦白，我不要冰冷快乐的回忆，我要你的体温，暖我一世的心晨。\\n\\n　　莫回首，回首需驻足，无怨无悔。\\n\\n　　既回首，风雨皆为尘，自始至终。\\n\\n　　与我一起摘下那只属于我们的四叶草，咫尺天涯，不离不弃。\",\"msgId\":\"d6e9ea65e9804f46b01378c18b7af79b\"}', 'mail.exchange', 'mail.routing.key', '3', '0', '2020-04-14 18:17:11', '2020-04-14 18:16:11', '2020-04-14 18:16:11');
