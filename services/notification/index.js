const express = require('express')
const sequelize = require('./database')
const user = require('./models/user')
const router = require('./routes/index')
const uncaughtErrorHandler = require('./middleware/uncaughtErrorHandler')
const apiErrorHandler = require("./middleware/apiErrorHandler");
const {uniqueConstraintErrorHandler} = require("./middleware/databaseErrorHandler");

const PORT = process.env.PORT || 8200

const app = express()

console.log(process.env)

app.use(express.json())
app.use('/api', router)
app.use(apiErrorHandler)
app.use(uniqueConstraintErrorHandler)
app.use(uncaughtErrorHandler)

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
