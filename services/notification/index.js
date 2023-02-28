const express = require('express')
const sequelize = require('./database')
const user = require('./models/user')
const router = require('./routes/index')

const PORT = process.env.PORT || 8200

const app = express()
app.use(express.json())
app.use('/api', router)

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
