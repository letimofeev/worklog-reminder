import {Controller, Inject, Post, Req, Res} from "@nestjs/common";
import {ActivityHandler, CloudAdapter, TurnContext} from "botbuilder";

@Controller('/api/bot/messages')
export class BotController {
    constructor(@Inject('BOT_ADAPTER') private adapter: CloudAdapter,
                @Inject('BOT_ACTIVITY_HANDLER') private activityHandler: ActivityHandler) {
    }

    @Post()
    async sendMessage(@Req() request, @Res() response) {
        await this.adapter.process(request, response, (context: TurnContext) =>
            this.activityHandler.run(context));
    }
}
