import {ArgumentMetadata, Injectable, PipeTransform} from "@nestjs/common";
import {ValidationException} from "../exceptions/validation.exception";
import {ApiError} from "../exceptions/handlers/api-error";
import {ValidationFailedApiSubError} from "../exceptions/handlers/validation-failed.api-sub-error";

@Injectable()
export class UserIdParamValidationPipe implements PipeTransform<number, number> {
    transform(value: number, metadata: ArgumentMetadata): number {
        if (value > 0) {
            return value;
        }

        const apiError = ApiError.badRequest('Validation failed', [
            new ValidationFailedApiSubError('user id must be greater than 0', value)
        ]);
        throw new ValidationException(apiError);
    }
}
