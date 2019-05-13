-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 07, 2018 at 12:41 AM
-- Server version: 10.1.35-MariaDB
-- PHP Version: 7.2.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mb`
--

-- --------------------------------------------------------

--
-- Table structure for table `brand`
--

CREATE TABLE `brand` (
  `bID` int(11) NOT NULL,
  `name` varchar(256) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `quantityWarehouse` int(11) DEFAULT '0',
  `quantityVehicle` int(11) DEFAULT '0',
  `unitsPerBox` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `brand`
--

INSERT INTO `brand` (`bID`, `name`, `weight`, `price`, `quantityWarehouse`, `quantityVehicle`, `unitsPerBox`) VALUES
(5, 'Cheese & Onion', 50, 26, 270, 180, 90),
(7, 'Milk Cream Smileys', 42, 17.5, 400, 200, 200),
(8, 'Orange Cream Smileys', 42, 17.5, 400, 200, 200),
(9, 'Chocalate Cream Smileys', 42, 17.5, 400, 200, 200),
(10, 'Gluco G', 70, 17.5, 350, 150, 50),
(11, 'Marie', 50, 17.5, 400, 200, 100),
(12, 'Mega Marie', 300, 85, 0, 24, 12),
(13, 'Tea Time', 160, 105, 20, 20, 20),
(14, 'Fancy Gem', 1000, 300, 23, 20, 5),
(15, 'Gift', 300, 215, 12, 12, 12),
(16, 'Milk Shorties', 300, 85, 140, 60, 4),
(17, 'Batter Rings', 280, 85, 220, 60, 4),
(18, 'Choco Ring ', 280, 85, 220, 60, 4),
(19, 'Lamon Puff', 200, 85, 30, 30, 30),
(20, 'Milk Biscuit', 300, 85, 140, 60, 4),
(21, 'Vanilla Cream Wafers', 225, 85, 84, 36, 12),
(22, 'Chocalate Cream Wafers', 225, 85, 84, 36, 12),
(23, 'Bourbon', 150, 53, 72, 48, 24),
(24, 'Strawberry Cream Biscuit', 100, 40, 30, 30, 30),
(25, 'Icing Wheat', 400, 130, 140, 60, 4),
(26, 'Vanilla Cream Wafers', 400, 170, 28, 12, 4),
(27, 'Ginger', 225, 85, 42, 18, 6),
(28, 'Chocalate Wafers', 35, 18, 120, 60, 60),
(29, 'Lamon Wafers', 35, 18, 60, 60, 60),
(30, 'Vanilla Wafers', 35, 18, 120, 60, 60),
(31, 'Strawberry Wafers', 35, 18, 60, 60, 60),
(32, 'Chocalate Wafers', 90, 44, 48, 24, 24),
(33, 'Strawberry Wafers', 90, 44, 24, 24, 24),
(34, 'Vanilla Wafers', 90, 44, 72, 24, 24),
(35, 'Chocalate Cream', 100, 44, 90, 60, 30),
(36, 'Chocalate Cream', 400, 155, 20, 20, 4),
(37, 'Nice ', 400, 130, 60, 20, 4);

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE `stock` (
  `sID` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `Date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `bID` int(11) NOT NULL,
  `type` varchar(256) NOT NULL,
  `billNumber` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`sID`, `quantity`, `Date`, `bID`, `type`, `billNumber`) VALUES
(39, 60, '2018-11-05 08:00:00', 27, 'Arrived', '7534'),
(40, 280, '2018-11-05 08:00:00', 17, 'Arrived', '7534'),
(41, 450, '2018-11-05 08:00:00', 5, 'Arrived', '7534'),
(42, 280, '2018-11-05 08:00:00', 18, 'Arrived', '7534'),
(43, 40, '2018-11-05 08:00:00', 36, 'Arrived', '7534'),
(44, 24, '2018-11-05 08:00:00', 15, 'Arrived', '7534'),
(45, 200, '2018-11-05 08:00:00', 25, 'Arrived', '7534'),
(46, 600, '2018-11-05 08:00:00', 11, 'Arrived', '7534'),
(47, 200, '2018-11-05 08:00:00', 20, 'Arrived', '7534'),
(48, 600, '2018-11-05 08:00:00', 7, 'Arrived', '7534'),
(49, 60, '2018-11-05 08:00:00', 24, 'Arrived', '7534'),
(50, 96, '2018-11-05 08:00:00', 34, 'Arrived', '7534'),
(51, 180, '2018-11-05 08:00:00', 28, 'Arrived', '7534'),
(52, 120, '2018-11-05 08:00:00', 29, 'Arrived', '7534'),
(53, 48, '2018-11-05 08:00:00', 33, 'Arrived', '7534'),
(54, 180, '2018-11-05 08:00:00', 30, 'Arrived', '7534'),
(55, 80, '2018-11-05 08:00:00', 37, 'Arrived', '7534'),
(56, 120, '2018-11-05 08:00:00', 23, 'Arrived', '7534'),
(57, 150, '2018-11-05 08:00:00', 35, 'Arrived', '7534'),
(58, 43, '2018-11-05 08:00:00', 14, 'Arrived', '7534'),
(59, 500, '2018-11-05 08:00:00', 10, 'Arrived', '7534'),
(60, 60, '2018-11-05 08:00:00', 19, 'Arrived', '7534'),
(61, 24, '2018-11-05 08:00:00', 12, 'Arrived', '7534'),
(62, 200, '2018-11-05 08:00:00', 16, 'Arrived', '7534'),
(63, 600, '2018-11-05 08:00:00', 8, 'Arrived', '7534'),
(64, 40, '2018-11-05 08:00:00', 13, 'Arrived', '7534'),
(65, 120, '2018-11-05 08:00:00', 22, 'Arrived', '7534'),
(66, 72, '2018-11-05 08:00:00', 32, 'Arrived', '7534'),
(67, 120, '2018-11-05 08:00:00', 31, 'Arrived', '7534'),
(68, 120, '2018-11-05 08:00:00', 21, 'Arrived', '7534'),
(69, 10, '2018-11-05 08:00:00', 26, 'Arrived', '7534'),
(70, 60, '2018-11-06 22:30:38', 18, 'Added To Vehicle', NULL),
(71, 60, '2018-11-06 22:30:47', 17, 'Added To Vehicle', NULL),
(72, 60, '2018-11-06 22:31:04', 20, 'Added To Vehicle', NULL),
(73, 60, '2018-11-06 22:31:15', 16, 'Added To Vehicle', NULL),
(74, 60, '2018-11-06 22:31:29', 25, 'Added To Vehicle', NULL),
(75, 20, '2018-11-06 22:33:08', 36, 'Added To Vehicle', NULL),
(76, 20, '2018-11-06 22:33:19', 37, 'Added To Vehicle', NULL),
(77, 20, '2018-11-06 22:35:01', 14, 'Added To Vehicle', NULL),
(78, 200, '2018-11-06 22:35:41', 11, 'Added To Vehicle', NULL),
(79, 180, '2018-11-06 22:35:58', 5, 'Added To Vehicle', NULL),
(80, 20, '2018-11-06 22:36:23', 13, 'Added To Vehicle', NULL),
(81, 12, '2018-11-06 22:37:28', 15, 'Added To Vehicle', NULL),
(82, 24, '2018-11-06 22:39:34', 12, 'Added To Vehicle', NULL),
(83, 30, '2018-11-06 22:39:44', 24, 'Added To Vehicle', NULL),
(84, 60, '2018-11-06 22:40:04', 35, 'Added To Vehicle', NULL),
(85, 36, '2018-11-06 22:40:28', 22, 'Added To Vehicle', NULL),
(86, 36, '2018-11-06 22:40:49', 21, 'Added To Vehicle', NULL),
(87, 60, '2018-11-06 22:42:26', 30, 'Added To Vehicle', NULL),
(88, 60, '2018-11-06 22:42:38', 28, 'Added To Vehicle', NULL),
(89, 60, '2018-11-06 22:42:49', 29, 'Added To Vehicle', NULL),
(90, 60, '2018-11-06 22:43:01', 31, 'Added To Vehicle', NULL),
(91, 150, '2018-11-06 22:43:30', 10, 'Added To Vehicle', NULL),
(92, 600, '2018-11-05 08:00:00', 9, 'Arrived', '7534'),
(93, 200, '2018-11-06 23:33:50', 9, 'Added To Vehicle', NULL),
(94, 200, '2018-11-06 23:35:03', 7, 'Added To Vehicle', NULL),
(95, 200, '2018-11-06 23:35:22', 8, 'Added To Vehicle', NULL),
(96, 30, '2018-11-06 23:35:32', 19, 'Added To Vehicle', NULL),
(97, 18, '2018-11-06 23:35:45', 27, 'Added To Vehicle', NULL),
(98, 48, '2018-11-06 23:36:07', 23, 'Added To Vehicle', NULL),
(99, 24, '2018-11-06 23:36:24', 32, 'Added To Vehicle', NULL),
(100, 24, '2018-11-06 23:36:43', 34, 'Added To Vehicle', NULL),
(101, 24, '2018-11-06 23:36:55', 33, 'Added To Vehicle', NULL),
(102, 30, '2018-11-05 08:00:00', 26, 'Arrived', '7534'),
(103, 12, '2018-11-06 23:38:17', 26, 'Added To Vehicle', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `brand`
--
ALTER TABLE `brand`
  ADD PRIMARY KEY (`bID`);

--
-- Indexes for table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`sID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `brand`
--
ALTER TABLE `brand`
  MODIFY `bID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `stock`
--
ALTER TABLE `stock`
  MODIFY `sID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=104;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
