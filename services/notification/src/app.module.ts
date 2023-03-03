import {Module} from '@nestjs/common';
import {UserModule} from "./users/user.module";
import {BotModule} from "./bots/bot.module";

@Module({
    imports: [UserModule, BotModule]
})
export class AppModule {}
