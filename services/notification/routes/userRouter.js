const express = require('express');
const userController = require('../contollers/userController')

const router = express.Router();

router.get('/', userController.getAll)
router.get('/:id', userController.getById)
router.post('/', userController.create)
router.patch('/', userController.update)
router.delete('/:id', userController.deleteById)


module.exports = router
