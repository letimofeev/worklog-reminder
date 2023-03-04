import {
    Controller,
    Get,
    Post,
    Req,
    Res
} from "@nestjs/common";
import {ActivityHandler, CloudAdapter, TurnContext} from "botbuilder";
import {UserService} from "../users/user.service";
import * as process from "process";
import {ConversationReference} from "botframework-schema";

@Controller('/api/bot')
export class BotController {
    constructor(private adapter: CloudAdapter,
                private activityHandler: ActivityHandler,
                private userService: UserService) {
    }

    @Post('/messages')
    async sendActivity(@Req() request, @Res() response) {
        await this.adapter.process(request, response, (context: TurnContext) =>
            this.activityHandler.run(context));
    }

    @Get('/notify')
    async notifyAll() {
        const users = await this.userService.getAll();
        users.forEach(user => {
            const conversationReference = user.conversationReference as Partial<ConversationReference>;
            this.adapter.continueConversationAsync(process.env.MicrosoftAppId, conversationReference,
                async (context) => {
                await context.sendActivity('notification');
            })
        })
    }
}
