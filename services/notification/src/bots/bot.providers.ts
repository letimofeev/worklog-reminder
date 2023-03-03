import {ActivityHandler, CloudAdapter, ConfigurationBotFrameworkAuthentication} from "botbuilder";
import {
    ConfigurationBotFrameworkAuthenticationOptions
} from "botbuilder-core/src/configurationBotFrameworkAuthentication";
import {BotActivityHandler} from "./bot.activity-handler";

export const botProviders = [
    {
        provide: CloudAdapter,
        useFactory: async () => {
            const botFrameworkAuthentication = new ConfigurationBotFrameworkAuthentication({
                MicrosoftAppId: process.env.MICROSOFT_APP_ID,
                MicrosoftAppPassword: process.env.MICROSOFT_APP_PASSWORD,
                MicrosoftAppType: process.env.MICROSOFT_APP_TYPE,
            } as ConfigurationBotFrameworkAuthenticationOptions);

            return new CloudAdapter(botFrameworkAuthentication);
        },
    },
    {
        provide: ActivityHandler,
        useClass: BotActivityHandler
    }
];
