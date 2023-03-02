import {Module} from '@nestjs/common';
import {UserModule} from "./users/user.module";

@Module({
    controllers: [],
    providers: [],
    imports: [UserModule]
})
export class AppModule {}
