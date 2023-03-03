export class RowsUpdatedResponseDto {
    constructor(rowsUpdated: number) {
        this.rowsUpdated = rowsUpdated;
    }

    rowsUpdated: number;
}
