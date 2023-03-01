const express = require('express');
const userRouter = require('./userRouter')
const botRouter = require('./botRouter')

const router = express.Router();

router.use('/users', userRouter)
router.use('/bot', botRouter)

module.exports = router
