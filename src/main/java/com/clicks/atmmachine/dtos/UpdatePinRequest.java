package com.clicks.atmmachine.dtos;

import com.clicks.atmmachine.model.AtmCard;

/**
 * DTO class that represents an update particulars to a particular ATM Card PIN
 * with old PIN and new PIN
 */
public record UpdatePinRequest(AtmCard atmCard, String oldPin, String newPin) {
}
