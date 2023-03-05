'use strict';

const {DataType} = require("sequelize-typescript");
/** @type {import('sequelize-cli').Migration} */
module.exports = {
    async up(queryInterface, Sequelize) {
        await queryInterface.changeColumn('users', 'skype_id', {
            type: DataType.STRING(128),
            unique: true,
            allowNull: false
        });
    },

    async down(queryInterface, Sequelize) {
        await queryInterface.changeColumn('users', 'skype_id', {
            type: DataType.STRING(64),
            unique: true,
            allowNull: false
        });
    }
};
