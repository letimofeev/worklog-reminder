import {Module} from "@nestjs/common";
import {botProviders} from "./bot.providers";
import {BotController} from "./bot.controller";
import {UserModule} from "../users/user.module";

@Module({
    imports: [UserModule],
    controllers: [BotController],
    providers: [...botProviders],
    exports: [...botProviders]
})
export class BotModule {}
