const express = require('express');
const userController = require('../contollers/userController')
const {validateGetUserById, validateCreateUser} = require('../validators/userValidator');

const router = express.Router();

router.get('/', userController.getAll)
router.get('/:id', validateGetUserById, userController.getById)
router.post('/', validateCreateUser, userController.create)
router.patch('/', userController.update)
router.delete('/:id', userController.deleteById)


module.exports = router
