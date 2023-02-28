const express = require('express');
const userController = require('../contollers/userController')
const {userIdValidation, createUserValidation, updateUserValidation} = require('../validators/userValidator');

const router = express.Router();

router.post('/', createUserValidation, userController.create)
router.get('/', userController.getAll)
router.get('/:id', userIdValidation, userController.getById)
router.patch('/', updateUserValidation, userController.update)
router.delete('/:id', userIdValidation, userController.deleteById)


module.exports = router
