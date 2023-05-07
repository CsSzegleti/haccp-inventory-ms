package hu.soft4d.resource.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryItemToMoveDto {
    private Long menuItemId;
    private int quantity;
}
