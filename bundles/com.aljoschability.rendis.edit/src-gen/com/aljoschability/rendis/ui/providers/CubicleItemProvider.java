/**
 */
package com.aljoschability.rendis.ui.providers;


import com.aljoschability.rendis.Cubicle;
import com.aljoschability.rendis.RendisFactory;
import com.aljoschability.rendis.RendisPackage;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link com.aljoschability.rendis.Cubicle} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class CubicleItemProvider
  extends ChannelNodeItemProvider
  implements
    IEditingDomainItemProvider,
    IStructuredItemContentProvider,
    ITreeItemContentProvider,
    IItemLabelProvider,
    IItemPropertySource
{
  /**
   * This constructs an instance from a factory and a notifier.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CubicleItemProvider(AdapterFactory adapterFactory)
  {
    super(adapterFactory);
  }

  /**
   * This returns the property descriptors for the adapted class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object)
  {
    if (itemPropertyDescriptors == null)
    {
      super.getPropertyDescriptors(object);

      addBuildingPropertyDescriptor(object);
      addFloorPropertyDescriptor(object);
      addRoomPropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Building feature.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void addBuildingPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_Cubicle_building_feature"), //$NON-NLS-1$
         getString("_UI_PropertyDescriptor_description", "_UI_Cubicle_building_feature", "_UI_Cubicle_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
         RendisPackage.Literals.CUBICLE__BUILDING,
         true,
         false,
         true,
         null,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Floor feature.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void addFloorPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_Cubicle_floor_feature"), //$NON-NLS-1$
         getString("_UI_PropertyDescriptor_description", "_UI_Cubicle_floor_feature", "_UI_Cubicle_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
         RendisPackage.Literals.CUBICLE__FLOOR,
         true,
         false,
         true,
         null,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Room feature.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void addRoomPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_Cubicle_room_feature"), //$NON-NLS-1$
         getString("_UI_PropertyDescriptor_description", "_UI_Cubicle_room_feature", "_UI_Cubicle_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
         RendisPackage.Literals.CUBICLE__ROOM,
         true,
         false,
         true,
         null,
         null,
         null));
  }

  /**
   * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
   * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
   * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object)
  {
    if (childrenFeatures == null)
    {
      super.getChildrenFeatures(object);
      childrenFeatures.add(RendisPackage.Literals.CUBICLE__DEVICES);
      childrenFeatures.add(RendisPackage.Literals.CUBICLE__WIRES);
    }
    return childrenFeatures;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EStructuralFeature getChildFeature(Object object, Object child)
  {
    // Check the type of the specified child object and return the proper feature to use for
    // adding (see {@link AddCommand}) it as a child.

    return super.getChildFeature(object, child);
  }

  /**
   * This returns Cubicle.gif.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object getImage(Object object)
  {
    return overlayImage(object, getResourceLocator().getImage("full/obj16/Cubicle")); //$NON-NLS-1$
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected boolean shouldComposeCreationImage() 
  {
    return true;
  }

  /**
   * This returns the label text for the adapted class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getText(Object object)
  {
    String label = ((Cubicle)object).getName();
    return label == null || label.length() == 0 ?
      getString("_UI_Cubicle_type") : //$NON-NLS-1$
      getString("_UI_Cubicle_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to update any cached
   * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void notifyChanged(Notification notification)
  {
    updateChildren(notification);

    switch (notification.getFeatureID(Cubicle.class))
    {
      case RendisPackage.CUBICLE__DEVICES:
      case RendisPackage.CUBICLE__WIRES:
        fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
        return;
    }
    super.notifyChanged(notification);
  }

  /**
   * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
   * that can be created under this object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
  {
    super.collectNewChildDescriptors(newChildDescriptors, object);

    newChildDescriptors.add
      (createChildParameter
        (RendisPackage.Literals.CUBICLE__DEVICES,
         RendisFactory.eINSTANCE.createResidualCurrentProtectiveDevice()));

    newChildDescriptors.add
      (createChildParameter
        (RendisPackage.Literals.CUBICLE__DEVICES,
         RendisFactory.eINSTANCE.createCircuitBreaker()));

    newChildDescriptors.add
      (createChildParameter
        (RendisPackage.Literals.CUBICLE__DEVICES,
         RendisFactory.eINSTANCE.createCounter()));

    newChildDescriptors.add
      (createChildParameter
        (RendisPackage.Literals.CUBICLE__DEVICES,
         RendisFactory.eINSTANCE.createStairwaySwitchTimer()));

    newChildDescriptors.add
      (createChildParameter
        (RendisPackage.Literals.CUBICLE__DEVICES,
         RendisFactory.eINSTANCE.createTransformator()));

    newChildDescriptors.add
      (createChildParameter
        (RendisPackage.Literals.CUBICLE__WIRES,
         RendisFactory.eINSTANCE.createWire()));
  }

}
