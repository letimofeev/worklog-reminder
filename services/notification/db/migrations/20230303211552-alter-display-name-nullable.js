'use strict';

const {DataType} = require("sequelize-typescript");
/** @type {import('sequelize-cli').Migration} */
module.exports = {
    async up(queryInterface, Sequelize) {
        await queryInterface.changeColumn('users', 'display_name', {
            field: 'display_name',
            type: DataType.STRING(64)
        });
    },

    async down(queryInterface, Sequelize) {
        await queryInterface.changeColumn('users', 'display_name', {
            field: 'display_name',
            type: DataType.STRING(64),
            allowNull: false
        });
    }
};
