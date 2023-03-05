import {TurnContext} from "botbuilder";
import {Injectable, Logger} from "@nestjs/common";
import {UserService} from "../users/user.service";
import {CreateUserDto} from "../users/dtos/create-user.dto";

const {ActivityHandler} = require("botbuilder");

@Injectable()
export class BotActivityHandler extends ActivityHandler {
    private readonly logger = new Logger(BotActivityHandler.name);

    constructor(private userService: UserService) {
        super();

        this.onMessage(async (context: TurnContext, next: () => Promise<void>) => {
            await context.sendActivity(`You sent '${context.activity.text}'`);
            await next();
        });

        this.onDialog(async (context: TurnContext, next: () => Promise<void>) => {
            if (context.activity.action === 'add') {
                const user = {
                    skypeId: context.activity.from.id,
                    login: context.activity.from.name,
                    conversationReference: TurnContext.getConversationReference(context.activity)
                } as CreateUserDto;

                const created = await userService.create(user)
                this.logger.log(`Saved user with id: ${created.id}`)
            }
            await next();
        });
    }
}
