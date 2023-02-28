const userService = require('../services/userService')

class UserController {
    async create(request, response, next) {
        try {
            const user = request.body
            const createdUser = await userService.create(user)
            return response.json(createdUser)
        } catch (e) {
            next(e)
        }
    }

    async getAll(request, response) {
        const users = await userService.getAll()
        return response.json(users)
    }

    async getById(request, response) {
        const {id} = request.params
        const user = await userService.getById(id)
        return response.json(user)
    }

    async update(request, response) {
        const user = request.body
        const rowsUpdated = await userService.update(user)
        return response.json({
            rowsUpdated: rowsUpdated
        })
    }

    async deleteById(request, response) {
        const {id} = request.params
        const rowsDeleted = await userService.deleteById(id)
        return response.json({
            rowsDeleted: rowsDeleted
        })
    }
}

module.exports = new UserController()
