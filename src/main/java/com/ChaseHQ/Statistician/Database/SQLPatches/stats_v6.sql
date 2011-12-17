/*
* Update resources for Minecraft 1.8
*/

-- -----------------------
-- Resource updates 
-- -----------------------

INSERT INTO `resource_desc` VALUES ('97', 'Hidden Silverfish');
INSERT INTO `resource_desc` VALUES ('98', 'Stone Bricks');
INSERT INTO `resource_desc` VALUES ('99', 'Huge Brown Mushroom');
INSERT INTO `resource_desc` VALUES ('100', 'Huge Red Mushroom');
INSERT INTO `resource_desc` VALUES ('101', 'Iron Bars');
INSERT INTO `resource_desc` VALUES ('102', 'Glass Pane');
INSERT INTO `resource_desc` VALUES ('103', 'Melon');
INSERT INTO `resource_desc` VALUES ('104', 'Pumpkin Stem');
INSERT INTO `resource_desc` VALUES ('105', 'Melon Stem');
INSERT INTO `resource_desc` VALUES ('106', 'Vines');
INSERT INTO `resource_desc` VALUES ('107', 'Fence Gate');
INSERT INTO `resource_desc` VALUES ('108', 'Brick Stairs');
INSERT INTO `resource_desc` VALUES ('109', 'Stone Brick Stairs');
INSERT INTO `resource_desc` VALUES ('360', 'Melon (Slice)');
INSERT INTO `resource_desc` VALUES ('361', 'Pumpkin Seeds');
INSERT INTO `resource_desc` VALUES ('362', 'Melon Seeds');
INSERT INTO `resource_desc` VALUES ('363', 'Raw Beef');
INSERT INTO `resource_desc` VALUES ('364', 'Steak');
INSERT INTO `resource_desc` VALUES ('365', 'Raw Chicken');
INSERT INTO `resource_desc` VALUES ('366', 'Cooked Chicken');
INSERT INTO `resource_desc` VALUES ('367', 'Rotten Flesh');
INSERT INTO `resource_desc` VALUES ('368', 'Ender Pearl');

-- ---------------------------
-- Update DBVersion To 6
-- --------------------------

UPDATE `config` SET `dbVersion` = 6;