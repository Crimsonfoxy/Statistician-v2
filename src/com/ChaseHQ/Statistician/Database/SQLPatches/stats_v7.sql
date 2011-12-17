/*
* Update resources for Minecraft 1.0.1
*/

-- -----------------------
-- Kill type updates 
-- -----------------------

INSERT INTO `kill_types` VALUES ('14', 'Suicide');
INSERT INTO `kill_types` VALUES ('15', 'Starvation');

-- -----------------------
-- Creature updates 
-- -----------------------

INSERT INTO `creatures` VALUES ('20', 'Blaze');
INSERT INTO `creatures` VALUES ('21', 'CaveSpider');
INSERT INTO `creatures` VALUES ('22', 'EnderDragon');
INSERT INTO `creatures` VALUES ('23', 'Enderman');
INSERT INTO `creatures` VALUES ('24', 'MagmaCube');
INSERT INTO `creatures` VALUES ('25', 'MushroomCow');
INSERT INTO `creatures` VALUES ('26', 'Silverfish');
INSERT INTO `creatures` VALUES ('27', 'Snowman');
INSERT INTO `creatures` VALUES ('28', 'Villager');

-- -----------------------
-- Resource updates 
-- -----------------------

INSERT INTO `resource_desc` VALUES ('110', 'Mycelium');
INSERT INTO `resource_desc` VALUES ('111', 'Lily Pad');
INSERT INTO `resource_desc` VALUES ('112', 'Nether Brick');
INSERT INTO `resource_desc` VALUES ('113', 'Nether Brick Fence');
INSERT INTO `resource_desc` VALUES ('114', 'Nether Brick Stairs');
INSERT INTO `resource_desc` VALUES ('115', 'Nether Wart');
INSERT INTO `resource_desc` VALUES ('116', 'Enchantment Table');
INSERT INTO `resource_desc` VALUES ('117', 'Brewing Stand');
INSERT INTO `resource_desc` VALUES ('118', 'Cauldron');
INSERT INTO `resource_desc` VALUES ('119', 'End Portal');
INSERT INTO `resource_desc` VALUES ('120', 'End Portal Frame');
INSERT INTO `resource_desc` VALUES ('121', 'End Stone');
INSERT INTO `resource_desc` VALUES ('122', 'Dragon Egg');
INSERT INTO `resource_desc` VALUES ('369', 'Blaze Rod');
INSERT INTO `resource_desc` VALUES ('370', 'Ghast Tear');
INSERT INTO `resource_desc` VALUES ('371', 'Gold Nugget');
INSERT INTO `resource_desc` VALUES ('372', 'Nether Wart');
INSERT INTO `resource_desc` VALUES ('373', 'Potions');
INSERT INTO `resource_desc` VALUES ('374', 'Glass Bottle');
INSERT INTO `resource_desc` VALUES ('375', 'Spider Eye');
INSERT INTO `resource_desc` VALUES ('376', 'Fermented Spider Eye');
INSERT INTO `resource_desc` VALUES ('377', 'Blaze Powder');
INSERT INTO `resource_desc` VALUES ('378', 'Magma Cream');
INSERT INTO `resource_desc` VALUES ('379', 'Brewing Stand');
INSERT INTO `resource_desc` VALUES ('380', 'Cauldron');
INSERT INTO `resource_desc` VALUES ('381', 'Eye of Ender');
INSERT INTO `resource_desc` VALUES ('382', 'Glistering Melon');
INSERT INTO `resource_desc` VALUES ('383', 'Spawner Egg');
INSERT INTO `resource_desc` VALUES ('2258', 'blocks Disc');
INSERT INTO `resource_desc` VALUES ('2259', 'chirp Disc');
INSERT INTO `resource_desc` VALUES ('2260', 'far Disc');
INSERT INTO `resource_desc` VALUES ('2261', 'mall Disc');
INSERT INTO `resource_desc` VALUES ('2262', 'mellohi Disc');
INSERT INTO `resource_desc` VALUES ('2263', 'stal Disc');
INSERT INTO `resource_desc` VALUES ('2264', 'strad Disc');
INSERT INTO `resource_desc` VALUES ('2265', 'ward Disc');
INSERT INTO `resource_desc` VALUES ('2266', '11 Disc');
UPDATE `resource_desc` SET `description` = 'Wooden Plank' WHERE `resource_id` = '5';
UPDATE `resource_desc` SET `description` = 'Dispenser' WHERE `resource_id` = '23';
UPDATE `resource_desc` SET `description` = 'Red Mushroom' WHERE `resource_id` = '40';
UPDATE `resource_desc` SET `description` = 'Brick Block' WHERE `resource_id` = '45';
UPDATE `resource_desc` SET `description` = 'Monster Spawner' WHERE `resource_id` = '52';
UPDATE `resource_desc` SET `description` = 'Diamond Ore' WHERE `resource_id` = '56';
UPDATE `resource_desc` SET `description` = 'Diamond Block' WHERE `resource_id` = '57';
UPDATE `resource_desc` SET `description` = 'Crafting Table' WHERE `resource_id` = '58';
UPDATE `resource_desc` SET `description` = 'Iron Door' WHERE `resource_id` = '71';
UPDATE `resource_desc` SET `description` = 'Redstone Torch (\"off\" state)' WHERE `resource_id` = '75';
UPDATE `resource_desc` SET `description` = 'Stone Button' WHERE `resource_id` = '77';
UPDATE `resource_desc` SET `description` = 'Flint and Steel' WHERE `resource_id` = '259';
UPDATE `resource_desc` SET `description` = 'Wooden Sword' WHERE `resource_id` = '268';
UPDATE `resource_desc` SET `description` = 'Gold Sword' WHERE `resource_id` = '283';
UPDATE `resource_desc` SET `description` = 'Leather Boots' WHERE `resource_id` = '301';
UPDATE `resource_desc` SET `description` = 'Raw Porkchop' WHERE `resource_id` = '319';
UPDATE `resource_desc` SET `description` = 'Cooked Porkchop' WHERE `resource_id` = '320';
UPDATE `resource_desc` SET `description` = 'Golden Apple' WHERE `resource_id` = '322';
UPDATE `resource_desc` SET `description` = 'Wooden door' WHERE `resource_id` = '324';
UPDATE `resource_desc` SET `description` = 'Iron door' WHERE `resource_id` = '330';
UPDATE `resource_desc` SET `description` = 'Dye' WHERE `resource_id` = '351';
UPDATE `resource_desc` SET `description` = '13 Disc' WHERE `resource_id` = '2256';
UPDATE `resource_desc` SET `description` = 'Cat Disc' WHERE `resource_id` = '2257';

-- ---------------------------
-- Update DBVersion To 7
-- --------------------------

UPDATE `config` SET `dbVersion` = 7;