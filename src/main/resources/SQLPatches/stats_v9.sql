/*
* Update for Minecraft 1.2.4
*/

-- -----------------------
-- Kill type updates
-- -----------------------

INSERT INTO `kill_types` VALUES ('18', 'Melting');

-- -----------------------
-- Creature updates
-- -----------------------

INSERT INTO `creatures` VALUES ('29', 'Ocelot');
INSERT INTO `creatures` VALUES ('30', 'Iron Golem');

-- -----------------------
-- Resource updates 
-- -----------------------

INSERT INTO `resource_desc` VALUES ('123', 'Redstone Lamp (inactive)');
INSERT INTO `resource_desc` VALUES ('124', 'Redstone Lamp (active)');

-- ---------------------------
-- Update DBVersion To 9
-- --------------------------

UPDATE `config` SET `dbVersion` = 9;