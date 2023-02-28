const {validationResult, param, body} = require('express-validator');
const ValidationFailedApiSubError = require("../error/validationFailedApiSubError");
const ApiError = require("../error/apiError");

const validate = (request, response, next) => {
    const errors = validationResult(request);
    if (!errors.isEmpty()) {
        const validationFailedErrors = errors.array()
            .map(error => new ValidationFailedApiSubError(error))
        const apiError = ApiError.badRequest('Validation failed', validationFailedErrors)
        return response.status(apiError.status).json(apiError)
    }
    next()
}

const userIdValidation = [
    param('id')
        .notEmpty().withMessage('id is required')
        .isInt({min: 1}).withMessage('id must be a positive integer'),
    validate
]

const createUserValidation = [
    body('skypeId')
        .notEmpty().withMessage('skypeId is required')
        .isLength({max: 64}).withMessage('skypeId must be less or equal than 64 symbols'),
    body('displayName')
        .notEmpty().withMessage('displayName is required')
        .isLength({max: 64}).withMessage('displayName must be less or equal than 64 symbols'),
    validate
]

const updateUserValidation = [
    body('id')
        .notEmpty().withMessage('id is required')
        .isInt({min: 1}).withMessage('id must be a positive integer'),
    body('skypeId')
        .notEmpty().withMessage('skypeId is required')
        .isLength({max: 64}).withMessage('skypeId must be less or equal than 64 symbols'),
    body('displayName')
        .notEmpty().withMessage('displayName is required')
        .isLength({max: 64}).withMessage('displayName must be less or equal than 64 symbols'),
    validate
]

module.exports = {
    userIdValidation,
    createUserValidation,
    updateUserValidation
}
