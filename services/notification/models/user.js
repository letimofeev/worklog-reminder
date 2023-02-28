const sequelize = require('../database')
const {DataTypes} = require('sequelize')

const User = sequelize.define('user', {
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true
    },
    skype_id: {
        type: DataTypes.STRING(64),
        unique: true
    },
    display_name: {
        type: DataTypes.STRING(64),
        allowNull: false
    },
    enabled: {
        type: DataTypes.BOOLEAN,
        defaultValue: true
    },
    conversation_reference: {
        type: DataTypes.JSONB
    }
})

module.exports = User
