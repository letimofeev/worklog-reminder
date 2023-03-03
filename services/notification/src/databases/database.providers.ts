import {Sequelize} from "sequelize-typescript";
import {User} from "../users/user.model";

export const databaseProviders = [
    {
        provide: 'SEQUELIZE',
        useFactory: async () => {
            const sequelize = new Sequelize(
                process.env.POSTGRES_DB || 'notification',
                process.env.POSTGRES_USER || 'postgres',
                process.env.POSTGRES_PASSWORD || 'postgres',
                {
                    dialect: 'postgres',
                    host: process.env.POSTGRES_HOST || 'localhost',
                    port: Number(process.env.POSTGRES_PORT) || 5433
                }
            );
            sequelize.addModels([User]);
            return sequelize;
        },
    },
];
