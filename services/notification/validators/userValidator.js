const {check} = require('express-validator');

const validateGetUserById = [
    check('id')
        .notEmpty().withMessage('id is required')
        .isInt({min: 1}).withMessage('user id must be a positive integer')
]

module.exports = validateGetUserById
