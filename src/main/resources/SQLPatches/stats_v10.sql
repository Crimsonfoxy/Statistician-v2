/*
* Update for Minecraft 1.3.1
*/

-- -----------------------
-- Resource updates 
-- -----------------------

INSERT INTO `resource_desc` VALUES ('125', 'Wooden Double Slab');
INSERT INTO `resource_desc` VALUES ('126', 'Wooden Slab');
INSERT INTO `resource_desc` VALUES ('127', 'Cocoa Plant');
INSERT INTO `resource_desc` VALUES ('128', 'Sandstone Stairs');
INSERT INTO `resource_desc` VALUES ('129', 'Emerald Ore');
INSERT INTO `resource_desc` VALUES ('130', 'Ender Chest');
INSERT INTO `resource_desc` VALUES ('131', 'Tripwire Hook');
INSERT INTO `resource_desc` VALUES ('132', 'Tripwire');
INSERT INTO `resource_desc` VALUES ('133', 'Block of Emerald');
INSERT INTO `resource_desc` VALUES ('134', 'Spruce Wood Stairs');
INSERT INTO `resource_desc` VALUES ('135', 'Birch Wood Stairs');
INSERT INTO `resource_desc` VALUES ('136', 'Jungle Wood Stairs');
INSERT INTO `resource_desc` VALUES ('384', 'Bottle o' Enchanting');
INSERT INTO `resource_desc` VALUES ('385', 'Fire Charge');
INSERT INTO `resource_desc` VALUES ('386', 'Book and Quill');
INSERT INTO `resource_desc` VALUES ('387', 'Written Book');
INSERT INTO `resource_desc` VALUES ('388', 'Emerald');

-- ---------------------------
-- Update DBVersion To 10
-- --------------------------

UPDATE `config` SET `dbVersion` = 10;