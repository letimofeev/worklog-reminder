import {Controller, Post, Req, Res} from "@nestjs/common";
import {ActivityHandler, CloudAdapter, TurnContext} from "botbuilder";

@Controller('/api/bot/messages')
export class BotController {
    constructor(private adapter: CloudAdapter, private activityHandler: ActivityHandler) {
    }

    @Post()
    async sendMessage(@Req() request, @Res() response) {
        await this.adapter.process(request, response, (context: TurnContext) =>
            this.activityHandler.run(context));
    }
}
