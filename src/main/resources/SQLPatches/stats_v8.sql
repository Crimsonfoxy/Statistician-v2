/*
* Update for Minecraft 1.1
*/

-- -----------------------
-- Kill type updates
-- -----------------------

INSERT INTO `kill_types` VALUES ('16', 'Poison');
INSERT INTO `kill_types` VALUES ('17', 'Magic');

-- ---------------------------
-- Update DBVersion To 8
-- --------------------------

UPDATE `config` SET `dbVersion` = 8;