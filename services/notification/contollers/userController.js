const userService = require('../services/userService')

class UserController {
    async createUser(request, response) {
        const user = request.body
        const createdUser = await userService.createUser(user)
        return response.json(createdUser)
    }

    async getAllUsers(request, response) {
        const users = await userService.getAllUsers()
        return response.json(users)
    }

    async getUserById(request, response) {
        const {id} = request.params
        const user = await userService.getUserById(id)
        return response.json(user)
    }

    async updateUser(request, response) {
        const user = request.body
        const rowsUpdated = await userService.updateUser(user)
        return response.json({
            rowsUpdated: rowsUpdated
        })
    }

    async deleteUserById(request, response) {
        const {id} = request.params
        const rowsDeleted = await userService.deleteUserById(id)
        return response.json({
            rowsDeleted: rowsDeleted
        })
    }
}

module.exports = new UserController()
