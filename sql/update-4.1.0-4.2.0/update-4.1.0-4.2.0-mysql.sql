alter table Country change column countryCode a3 varchar(75);
alter table Country add a2 varchar(75) null;
alter table Country add number_ varchar(75) null;
alter table Country add idd_ varchar(75) null;

update Country SET a2 = 'CA', number_ = '124', idd_ = '001' WHERE countryId = '1';
update Country SET a2 = 'CN', number_ = '156', idd_ = '086' WHERE countryId = '2';
update Country SET a2 = 'FR', number_ = '250', idd_ = '033' WHERE countryId = '3';
update Country SET a2 = 'DE', number_ = '276', idd_ = '049' WHERE countryId = '4';
update Country SET a2 = 'HK', number_ = '344', idd_ = '852' WHERE countryId = '5';
update Country SET a2 = 'HU', number_ = '348', idd_ = '036' WHERE countryId = '6';
update Country SET a2 = 'IL', number_ = '376', idd_ = '972' WHERE countryId = '7';
update Country SET a2 = 'IT', number_ = '380', idd_ = '039' WHERE countryId = '8';
update Country SET a2 = 'JP', number_ = '392', idd_ = '081' WHERE countryId = '9';
update Country SET a2 = 'KP', number_ = '410', idd_ = '082' WHERE countryId = '10';
update Country SET a2 = 'NL', number_ = '528', idd_ = '031' WHERE countryId = '11';
update Country SET a2 = 'PT', number_ = '620', idd_ = '351' WHERE countryId = '12';
update Country SET a2 = 'RU', number_ = '643', idd_ = '007' WHERE countryId = '13';
update Country SET a2 = 'SG', number_ = '702', idd_ = '065' WHERE countryId = '14';
update Country SET a2 = 'ES', number_ = '724', idd_ = '034' WHERE countryId = '15';
update Country SET a2 = 'TR', number_ = '792', idd_ = '090' WHERE countryId = '16';
update Country SET a2 = 'VM', number_ = '704', idd_ = '084' WHERE countryId = '17';
update Country SET a2 = 'GB', number_ = '826', idd_ = '044' WHERE countryId = '18';
update Country SET a2 = 'US', number_ = '840', idd_ = '001' WHERE countryId = '19';

insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('20', 'Afghanistan', 'AF', 'AFG', '4', '093', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('21', 'Albania', 'AL', 'ALB', '8', '355', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('22', 'Algeria', 'DZ', 'DZA', '12', '213', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('23', 'American Samoa', 'AS', 'ASM', '16', '684', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('24', 'Andorra', 'AD', 'AND', '20', '376', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('25', 'Angola', 'AO', 'AGO', '24', '244', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('26', 'Anguilla', 'AI', 'AIA', '660', '264', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('27', 'Antarctica', 'AQ', 'ATA', '10', '672', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('28', 'Antigua', 'AG', 'ATG', '28', '268', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('29', 'Argentina', 'AR', 'ARG', '32', '054', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('30', 'Armenia', 'AM', 'ARM', '51', '374', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('31', 'Aruba', 'AW', 'ABW', '533', '297', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('32', 'Australia', 'AU', 'AUS', '36', '061', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('33', 'Austria', 'AT', 'AUT', '40', '043', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('34', 'Azerbaijan', 'AZ', 'AZE', '31', '994', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('35', 'Bahamas', 'BS', 'BHS', '44', '242', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('36', 'Bahrain', 'BH', 'BHR', '48', '973', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('37', 'Bangladesh', 'BD', 'BGD', '50', '880', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('38', 'Barbados', 'BB', 'BRB', '52', '246', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('39', 'Belarus', 'BY', 'BLR', '112', '375', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('40', 'Belgium', 'BE', 'BEL', '56', '032', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('41', 'Belize', 'BZ', 'BLZ', '84', '501', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('42', 'Benin', 'BJ', 'BEN', '204', '229', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('43', 'Bermuda', 'BM', 'BMU', '60', '441', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('44', 'Bhutan', 'BT', 'BTN', '64', '975', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('45', 'Bolivia', 'BO', 'BOL', '68', '591', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('46', 'Bosnia-Herzegovina', 'BA', 'BIH', '70', '387', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('47', 'Botswana', 'BW', 'BWA', '72', '267', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('48', 'Brazil', 'BR', 'BRA', '76', '055', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('49', 'British Virgin Islands', 'VG', 'VGB', '92', '284', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('50', 'Brunei', 'BN', 'BRN', '96', '673', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('51`', 'Bulgaria', 'BG', 'BGR', '100', '359', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('52', 'Burkina Faso', 'BF', 'BFA', '854', '226', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('53', 'Burma (Myanmar)', 'MM', 'MMR', '104', '095', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('54', 'Burundi', 'BI', 'BDI', '108', '257', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('55', 'Cambodia', 'KH', 'KHM', '116', '855', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('56', 'Cameroon', 'CM', 'CMR', '120', '237', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('57', 'Cape Verde Island', 'CV', 'CPV', '132', '238', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('58', 'Cayman Islands', 'KY', 'CYM', '136', '345', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('59', 'Central African Republic', 'CF', 'CAF', '140', '236', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('60', 'Chad', 'TD', 'TCD', '148', '235', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('61', 'Chile', 'CL', 'CHL', '152', '056', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('62', 'Christmas Island', 'CX', 'CXR', '162', '061', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('63', 'Cocos Islands', 'CC', 'CCK', '166', '061', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('64', 'Colombia', 'CO', 'COL', '170', '057', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('65', 'Comoros', 'KM', 'COM', '174', '269', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('66', 'Republic of Congo', 'CD', 'COD', '180', '242', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('67', 'Democratic Republic of Congo', 'CG', 'COG', '178', '243', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('68', 'Cook Islands', 'CK', 'COK', '184', '682', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('69', 'Costa Rica', 'CI', 'CRI', '188', '506', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('70', 'Croatia', 'HR', 'HRV', '191', '385', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('71', 'Cuba', 'CU', 'CUB', '192', '053', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('72', 'Cyprus', 'CY', 'CYP', '196', '357', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('73', 'Czech Republic', 'CZ', 'CZE', '203', '420', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('74', 'Denmark', 'DK', 'DNK', '208', '045', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('75', 'Djibouti', 'DJ', 'DJI', '262', '253', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('76', 'Dominica', 'DM', 'DMA', '212', '767', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('77', 'Dominican Republic', 'DO', 'DOM', '214', '809', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('78', 'Ecuador', 'EC', 'ECU', '218', '593', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('79', 'Egypt', 'EG', 'EGY', '818', '020', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('80', 'El Salvador', 'SV', 'SLV', '222', '503', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('81', 'Equatorial Guinea', 'GQ', 'GNQ', '226', '240', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('82', 'Eritrea', 'ER', 'ERI', '232', '291', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('83', 'Estonia', 'EE', 'EST', '233', '372', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('84', 'Ethiopia', 'ET', 'ETH', '231', '251', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('85', 'Faeroe Islands', 'FO', 'FRO', '234', '298', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('86', 'Falkland Islands', 'FK', 'FLK', '238', '500', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('87', 'Fiji Islands', 'FJ', 'FJI', '242', '679', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('88', 'Finland', 'FI', 'FIN', '246', '358', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('89', 'French Guiana', 'GF', 'GUF', '254', '594', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('90', 'French Polynesia', 'PF', 'PYF', '258', '689', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('91', 'Gabon', 'GA', 'GAB', '266', '241', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('92', 'Gambia', 'GM', 'GMB', '270', '220', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('93', 'Georgia', 'GE', 'GEO', '268', '995', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('94', 'Ghana', 'GH', 'GHA', '288', '233', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('95', 'Gibraltar', 'GI', 'GIB', '292', '350', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('96', 'Greece', 'GR', 'GRC', '300', '030', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('97', 'Greenland', 'GL', 'GRL', '304', '299', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('98', 'Grenada', 'GD', 'GRD', '308', '473', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('99', 'Guadeloupe', 'GP', 'GLP', '312', '590', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('100', 'Guam', 'GU', 'GUM', '316', '671', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('101', 'Guatemala', 'GT', 'GTM', '320', '502', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('102', 'Guinea', 'GN', 'GIN', '324', '224', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('103', 'Guinea-Bissau', 'GW', 'GNB', '624', '245', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('104', 'Guyana', 'GY', 'GUY', '328', '592', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('105', 'Haiti', 'HT', 'HTI', '332', '509', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('106', 'Honduras', 'HN', 'HND', '340', '504', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('107', 'Iceland', 'IS', 'ISL', '352', '354', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('108', 'India', 'IN', 'IND', '356', '091', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('109', 'Indonesia', 'ID', 'IDN', '360', '062', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('110', 'Iran', 'IR', 'IRN', '364', '098', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('111', 'Iraq', 'IQ', 'IRQ', '368', '964', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('112', 'Ireland', 'IE', 'IRL', '372', '353', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('113', 'Ivory Coast', 'CI', 'CIV', '384', '225', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('114', 'Jamaica', 'JM', 'JAM', '388', '876', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('115', 'Jordan', 'JO', 'JOR', '400', '962', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('116', 'Kazakhstan', 'KZ', 'KAZ', '398', '007', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('117', 'Kenya', 'KE', 'KEN', '404', '254', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('118', 'Kiribati', 'KI', 'KIR', '408', '686', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('119', 'Kuwait', 'KW', 'KWT', '414', '965', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('120', 'North Korea', 'KP', 'PRK', '408', '850', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('121', 'Kyrgyzstan', 'KG', 'KGZ', '471', '996', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('122', 'Laos', 'LA', 'LAO', '418', '856', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('123', 'Latvia', 'LV', 'LVA', '428', '371', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('124', 'Lebanon', 'LB', 'LBN', '422', '961', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('125', 'Lesotho', 'LS', 'LSO', '426', '266', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('126', 'Liberia', 'LR', 'LBR', '430', '231', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('127', 'Libya', 'LY', 'LBY', '434', '218', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('128', 'Liechtenstein', 'LI', 'LIE', '438', '423', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('129', 'Lithuania', 'LT', 'LTU', '440', '370', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('130', 'Luxembourg', 'LU', 'LUX', '442', '352', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('131', 'Macau', 'MO', 'MAC', '446', '853', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('132', 'Macedonia', 'MK', 'MKD', '807', '389', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('133', 'Madagascar', 'MG', 'MDG', '450', '261', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('134', 'Malawi', 'MW', 'MWI', '454', '265', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('135', 'Malaysia', 'MY', 'MYS', '458', '060', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('136', 'Maldives', 'MV', 'MDV', '462', '960', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('137', 'Mali', 'ML', 'MLI', '466', '223', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('138', 'Malta', 'MT', 'MLT', '470', '356', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('139', 'Marshall Islands', 'MH', 'MHL', '584', '692', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('140', 'Martinique', 'MQ', 'MTQ', '474', '596', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('141', 'Mauritania', 'MR', 'MRT', '478', '222', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('142', 'Mauritius', 'MU', 'MUS', '480', '230', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('143', 'Mayotte Island', 'YT', 'MYT', '175', '269', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('144', 'Mexico', 'MX', 'MEX', '484', '052', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('145', 'Micronesia', 'FM', 'FSM', '583', '691', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('146', 'Moldova', 'MD', 'MDA', '498', '373', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('147', 'Monaco', 'MC', 'MCO', '492', '377', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('148', 'Mongolia', 'MN', 'MNG', '496', '976', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('149', 'Montserrat', 'MS', 'MSR', '500', '664', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('150', 'Morocco', 'MA', 'MAR', '504', '212', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('151', 'Mozambique', 'MZ', 'MOZ', '508', '258', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('152', 'Myanmar (Burma)', 'MM', 'MMR', '104', '095', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('153', 'Namibia', 'NA', 'NAM', '516', '264', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('154', 'Nauru', 'NR', 'NRU', '520', '674', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('155', 'Nepal', 'NP', 'NPL', '524', '977', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('156', 'Netherlands Antilles', 'AN', 'ANT', '530', '599', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('157', 'New Caledonia', 'NC', 'NCL', '540', '687', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('158', 'New Zealand', 'NZ', 'NZL', '554', '064', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('159', 'Nicaragua', 'NE', 'NER', '558', '505', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('160', 'Niger', 'NE', 'NER', '562', '227', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('161', 'Nigeria', 'NG', 'NGA', '566', '234', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('162', 'Niue', 'NU', 'NIU', '570', '683', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('163', 'Norfolk Island', 'NF', 'NFK', '574', '672', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('164', 'Norway', 'NO', 'NOR', '578', '047', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('165', 'Oman', 'OM', 'OMN', '512', '968', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('166', 'Pakistan', 'PK', 'PAK', '586', '092', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('167', 'Palau', 'PW', 'PLW', '585', '680', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('168', 'Palestine', 'PS', 'PSE', '275', '970', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('169', 'Panama', 'PA', 'PAN', '591', '507', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('170', 'Papua New Guinea', 'PG', 'PNG', '598', '675', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('171', 'Paraguay', 'PY', 'PRY', '600', '595', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('172', 'Peru', 'PE', 'PER', '604', '051', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('173', 'Philippines', 'PH', 'PHL', '608', '063', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('174', 'Poland', 'PL', 'POL', '616', '048', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('175', 'Puerto Rico', 'PR', 'PRI', '630', '787', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('176', 'Qatar', 'QA', 'QAT', '634', '974', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('177', 'Reunion Island', 'RE', 'REU', '638', '262', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('178', 'Romania', 'RO', 'ROU', '642', '040', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('179', 'Rwanda', 'RW', 'RWA', '646', '250', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('180', 'St. Helena', 'SH', 'SHN', '654', '290', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('181', 'St. Kitts', 'KN', 'KNA', '659', '869', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('182', 'St. Lucia', 'LC', 'LCA', '662', '758', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('183', 'St. Pierre & Miquelon', 'PM', 'SPM', '666', '508', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('184', 'St. Vincent', 'VC', 'VCT', '670', '784', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('185', 'San Marino', 'SM', 'SMR', '674', '378', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('186', 'Sao Tome & Principe', 'ST', 'STP', '678', '239', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('187', 'Saudi Arabia', 'SA', 'SAU', '682', '966', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('188', 'Senegal', 'SN', 'SEN', '686', '221', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('189', 'Serbia', 'CS', 'SCG', '891', '381', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('190', 'Seychelles', 'SC', 'SYC', '690', '248', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('191', 'Sierra Leone', 'SL', 'SLE', '694', '249', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('192', 'Slovakia', 'SK', 'SVK', '703', '421', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('193', 'Slovenia', 'SI', 'SVN', '705', '386', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('194', 'Solomon Islands', 'SB', 'SLB', '90', '677', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('195', 'Somalia', 'SO', 'SOM', '706', '252', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('196', 'South Africa', 'ZA', 'ZAF', '710', '027', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('197', 'Sri Lanka', 'LK', 'LKA', '144', '094', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('198', 'Sudan', 'SD', 'SDN', '736', '095', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('199', 'Suriname', 'SR', 'SUR', '740', '597', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('200', 'Swaziland', 'SZ', 'SWZ', '748', '268', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('201', 'Sweden', 'SE', 'SWE', '752', '046', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('202', 'Switzerland', 'CH', 'CHE', '756', '041', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('203', 'Syria', 'SY', 'SYR', '760', '963', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('204', 'Taiwan', 'TW', 'TWN', '158', '886', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('205', 'Tajikistan', 'TJ', 'TJK', '762', '992', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('206', 'Tanzania', 'TZ', 'TZA', '834', '255', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('207', 'Thailand', 'TH', 'THA', '764', '066', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('208', 'Togo', 'TG', 'TGO', '768', '228', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('209', 'Tonga', 'TO', 'TON', '776', '676', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('210', 'Trinidad & Tobago', 'TT', 'TTO', '780', '868', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('211', 'Tunisia', 'TN', 'TUN', '788', '216', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('212', 'Turkmenistan', 'TM', 'TKM', '795', '993', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('213', 'Turks & Caicos', 'TC', 'TCA', '796', '649', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('214', 'Tuvalu', 'TV', 'TUV', '798', '688', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('215', 'Uganda', 'UG', 'UGA', '800', '256', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('216', 'Ukraine', 'UA', 'UKR', '804', '380', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('217', 'United Arab Emirates', 'AE', 'ARE', '784', '971', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('218', 'Uruguay', 'UY', 'URY', '858', '598', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('219', 'Uzbekistan', 'UZ', 'UZB', '860', '998', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('220', 'Vanuatu', 'VU', 'VUT', '548', '678', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('221', 'Vatican City', 'VA', 'VAT', '336', '039', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('222', 'Venezuela', 'VE', 'VEN', '862', '058', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('223', 'Wallis & Futuna', 'WF', 'WLF', '876', '681', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('224', 'Western Samoa', 'EH', 'ESH', '732', '685', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('225', 'Yemen', 'YE', 'YEM', '887', '967', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('226', 'Yugoslavia', 'MK', 'MKD', '446', '381', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('227', 'Zambia', 'ZM', 'ZMB', '894', '260', 1);
insert into Country (countryId, name, a2, a3, number_, idd_, active_) values ('228', 'Zimbabwe', 'ZW', 'ZWE', '716', '263', 1);

alter table DLFileEntry add extraSettings longtext null;

create table DLFileShortcut (
	fileShortcutId integer not null primary key,
	companyId varchar(75) not null,
	userId varchar(75) not null,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	folderId varchar(75) null,
	toFolderId varchar(75) null,
	toName varchar(75) null
);

alter table Image add type_ varchar(75) null;

alter table JournalArticle add description longtext null;

alter table JournalArticle drop primary key;
alter table JournalArticle add primary key (companyId, groupId, articleId, version);

alter table JournalStructure drop primary key;
alter table JournalStructure add primary key (companyId, groupId, structureId);

alter table JournalTemplate drop primary key;
alter table JournalTemplate add primary key (companyId, groupId, templateId);

drop table JournalContentSearch;
create table JournalContentSearch (
	portletId varchar(75) not null,
	layoutId varchar(75) not null,
	ownerId varchar(75) not null,
	articleId varchar(75) not null,
	companyId varchar(75) not null,
	groupId varchar(75) not null,
	primary key (portletId, layoutId, ownerId, articleId)
);

alter table Layout add title longtext null;

alter table LayoutSet add virtualHost varchar(75) null;

alter table MBThread add priority double;

create table RatingsEntry (
	entryId integer not null primary key,
	companyId varchar(75) not null,
	userId varchar(75) not null,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	className varchar(75) null,
	classPK varchar(75) null,
	score double
);

create table RatingsStats (
	statsId integer not null primary key,
	className varchar(75) null,
	classPK varchar(75) null,
	totalEntries integer,
	totalScore double,
	averageScore double
);
