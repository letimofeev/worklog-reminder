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
                    displayName: context.activity.from.name,
                    conversationReference: TurnContext.getConversationReference(context.activity)
                } as CreateUserDto;

                const [createdOrFoundUser, isCreated] = await userService.findOrCreate(user)
                if (isCreated) {
                    this.logger.log(`Saved user with id: ${createdOrFoundUser.id}`)
                } else {
                    await this.userService.update({id: createdOrFoundUser.id, ...user});
                    this.logger.log(`User already existed, updated user with id = ${createdOrFoundUser.id}`);
                }
            }
            await next();
        });
    }
}
