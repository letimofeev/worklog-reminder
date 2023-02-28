const express = require('express')

const sequelize = require('./database')
const user = require('./models/user')

const PORT = process.env.PORT || 8200

const app = express()

const start = async () => {
    try {
        await sequelize.authenticate()
        await sequelize.sync()
        app.listen(PORT, () => console.log(`\nServer started on port ${PORT}`))
    } catch (e) {
        console.log(e)
    }
}

start()
