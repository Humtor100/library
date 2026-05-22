package com.kirito.library.dto;

import java.util.Date;
import java.util.UUID;

public record PersonResponse(UUID id, String name, Date birthday)

{
}
