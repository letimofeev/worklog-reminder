import {Module} from "@nestjs/common";
import {botProviders} from "./bot.providers";
import {BotController} from "./bot.controller";

@Module({
    controllers: [BotController],
    providers: [...botProviders],
    exports: [...botProviders]
})
export class BotModule {}
