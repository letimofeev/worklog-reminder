import {NestFactory} from '@nestjs/core';
import * as process from 'process';
import {AppModule} from './app.module';
import {Sequelize} from "sequelize";
import * as Umzug from "umzug";

const sequelize = new Sequelize(process.env.POSTGRES_DB || 'notification',
    process.env.POSTGRES_USER || 'postgres',
    process.env.POSTGRES_PASSWORD || 'postgres',
    {
        dialect: 'postgres',
        host: process.env.POSTGRES_HOST || 'localhost',
        port: Number(process.env.POSTGRES_PORT) || 5433
    })

const umzug = new Umzug({
    migrations: {
        path: './db/migrations',
        params: [
            sequelize.getQueryInterface(),
            Sequelize
        ]
    },
    storage: 'sequelize',
    storageOptions: {
        sequelize: sequelize
    }
});

async function runMigrations() {
    const pendingMigrations = await umzug.pending();
    if (pendingMigrations.length === 0) {
        console.log('No pending migrations');
        return;
    }

    console.log(`Pending migrations: ${pendingMigrations.length}`);
    await umzug.up();
    console.log('Migrations done');
}

runMigrations()
    .then(() => {
        sequelize.close();
    })
    .catch((err) => {
        console.error(err);
        sequelize.close();
    });

async function bootstrap() {
    const PORT = process.env.PORT || 8200;
    const app = await NestFactory.create(AppModule);

    await app.listen(PORT, () => console.log(`Server stated on port = ${PORT}`));
}

bootstrap();
