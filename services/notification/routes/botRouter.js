const express = require('express');
const botController = require('../contollers/botController')

const router = express.Router();

router.post('/messages', botController.sendMessage)

module.exports = router
