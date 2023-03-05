import {Module} from "@nestjs/common";
import {botProviders} from "./bot.providers";
import {BotController} from "./bot.controller";
import {DatabaseModule} from "../databases/database.module";
import {UserModule} from "../users/user.module";

@Module({
    imports: [DatabaseModule, UserModule],
    controllers: [BotController],
    providers: [...botProviders],
    exports: [...botProviders]
})
export class BotModule {}
