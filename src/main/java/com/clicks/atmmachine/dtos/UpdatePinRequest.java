package com.clicks.atmmachine.dtos;

import com.clicks.atmmachine.model.AtmCard;

public record UpdatePinRequest(AtmCard atmCard, String oldPin, String newPin) {
}
