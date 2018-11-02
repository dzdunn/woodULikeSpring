package com.dunn.validation.priority;

import com.dunn.model.user.WoodulikeUser;

import javax.validation.GroupSequence;

@GroupSequence({ FirstPriority.class, SecondPriority.class, ThirdPriority.class, WoodulikeUser.class})
public interface IWoodulikeOrderedValidation {
}
