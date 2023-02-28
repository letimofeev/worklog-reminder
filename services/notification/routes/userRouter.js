const express = require('express');
const userController = require('../contollers/userController')

const router = express.Router();

router.get('/', userController.getAllUsers)
router.get('/:id', userController.getUserById)
router.post('/', userController.createUser)
router.patch('/', userController.updateUser)
router.delete('/:id', userController.deleteUserById)


module.exports = router
