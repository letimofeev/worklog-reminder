export class UpdateUserResponseDto {
    constructor(rowsUpdated: number) {
        this.rowsUpdated = rowsUpdated;
    }

    rowsUpdated: number;
}
