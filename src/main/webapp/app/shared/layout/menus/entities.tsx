import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/country">
      Country
    </MenuItem>
    <MenuItem icon="asterisk" to="/country-data">
      Country Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/city">
      City
    </MenuItem>
    <MenuItem icon="asterisk" to="/city-data">
      City Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/state">
      State
    </MenuItem>
    <MenuItem icon="asterisk" to="/state-data">
      State Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/address">
      Address
    </MenuItem>
    <MenuItem icon="asterisk" to="/emergency-contact">
      Emergency Contact
    </MenuItem>
    <MenuItem icon="asterisk" to="/doctor">
      Doctor
    </MenuItem>
    <MenuItem icon="asterisk" to="/plan">
      Plan
    </MenuItem>
    <MenuItem icon="asterisk" to="/plan-data">
      Plan Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/language">
      Language
    </MenuItem>
    <MenuItem icon="asterisk" to="/coupon">
      Coupon
    </MenuItem>
    <MenuItem icon="asterisk" to="/coupon-type">
      Coupon Type
    </MenuItem>
    <MenuItem icon="asterisk" to="/allergy">
      Allergy
    </MenuItem>
    <MenuItem icon="asterisk" to="/allergy-data">
      Allergy Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/favourite-food">
      Favourite Food
    </MenuItem>
    <MenuItem icon="asterisk" to="/favourite-food-data">
      Favourite Food Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/feeding">
      Feeding
    </MenuItem>
    <MenuItem icon="asterisk" to="/feeding-data">
      Feeding Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/interest">
      Interest
    </MenuItem>
    <MenuItem icon="asterisk" to="/interest-data">
      Interest Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/medical-condition">
      Medical Condition
    </MenuItem>
    <MenuItem icon="asterisk" to="/medical-condition-data">
      Medical Condition Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/pet-type">
      Pet Type
    </MenuItem>
    <MenuItem icon="asterisk" to="/pet-type-data">
      Pet Type Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/pet-breed">
      Pet Breed
    </MenuItem>
    <MenuItem icon="asterisk" to="/pet-breed-data">
      Pet Breed Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/walking-other">
      Walking Other
    </MenuItem>
    <MenuItem icon="asterisk" to="/walking-other-data">
      Walking Other Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/rule">
      Rule
    </MenuItem>
    <MenuItem icon="asterisk" to="/rule-data">
      Rule Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/child-relation">
      Child Relation
    </MenuItem>
    <MenuItem icon="asterisk" to="/child-relation-data">
      Child Relation Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/supported-relation">
      Supported Relation
    </MenuItem>
    <MenuItem icon="asterisk" to="/supported-relation-data">
      Supported Relation Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/family-gallery">
      Family Gallery
    </MenuItem>
    <MenuItem icon="asterisk" to="/family">
      Family
    </MenuItem>
    <MenuItem icon="asterisk" to="/pet">
      Pet
    </MenuItem>
    <MenuItem icon="asterisk" to="/child">
      Child
    </MenuItem>
    <MenuItem icon="asterisk" to="/supported">
      Supported
    </MenuItem>
    <MenuItem icon="asterisk" to="/friend-request">
      Friend Request
    </MenuItem>
    <MenuItem icon="asterisk" to="/friendship">
      Friendship
    </MenuItem>
    <MenuItem icon="asterisk" to="/friendship-group">
      Friendship Group
    </MenuItem>
    <MenuItem icon="asterisk" to="/help-request">
      Help Request
    </MenuItem>
    <MenuItem icon="asterisk" to="/pet-help-request">
      Pet Help Request
    </MenuItem>
    <MenuItem icon="asterisk" to="/house-help-request">
      House Help Request
    </MenuItem>
    <MenuItem icon="asterisk" to="/garden-help-request">
      Garden Help Request
    </MenuItem>
    <MenuItem icon="asterisk" to="/supported-help-request">
      Supported Help Request
    </MenuItem>
    <MenuItem icon="asterisk" to="/child-help-request">
      Child Help Request
    </MenuItem>
    <MenuItem icon="asterisk" to="/invitation">
      Invitation
    </MenuItem>
    <MenuItem icon="asterisk" to="/message">
      Message
    </MenuItem>
    <MenuItem icon="asterisk" to="/message-item">
      Message Item
    </MenuItem>
    <MenuItem icon="asterisk" to="/notification">
      Notification
    </MenuItem>
    <MenuItem icon="asterisk" to="/payment">
      Payment
    </MenuItem>
    <MenuItem icon="asterisk" to="/public-holiday">
      Public Holiday
    </MenuItem>
    <MenuItem icon="asterisk" to="/minding-notification">
      Minding Notification
    </MenuItem>
    <MenuItem icon="asterisk" to="/user-identification">
      User Identification
    </MenuItem>
    <MenuItem icon="asterisk" to="/working-with-children">
      Working With Children
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
