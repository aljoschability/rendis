package com.aljoschability.rendis.ui.wizards;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramLink;
import org.eclipse.graphiti.mm.pictograms.PictogramsFactory;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.aljoschability.rendis.RendisFactory;
import com.aljoschability.rendis.Residence;

public class CreateResidenceDiagramWizard extends Wizard implements INewWizard {
	@Override
	public void addPages() {
		addPage(new CreateResidenceWizardPage("empty"));
	}

	@Override
	public boolean performFinish() {
		ResourceSet resourceSet = new ResourceSetImpl();

		String base = "tests/test" + System.currentTimeMillis();

		// model
		String modelPath = base + ".rendis";
		URI modelUri = URI.createPlatformResourceURI(modelPath, true);
		Resource modelResource = resourceSet.createResource(modelUri);

		Residence model = RendisFactory.eINSTANCE.createResidence();
		model.setId(EcoreUtil.generateUUID());

		modelResource.getContents().add(model);

		// diagram
		String diagramPath = base + ".rendis_diagrams";
		URI diagramUri = URI.createPlatformResourceURI(diagramPath, true);
		Resource diagramResource = resourceSet.createResource(diagramUri);

		String dtid = Residence.class.getCanonicalName();
		Diagram diagram = Graphiti.getPeService().createDiagram(dtid, model.getId(), true);

		PictogramLink link = PictogramsFactory.eINSTANCE.createPictogramLink();
		link.getBusinessObjects().add(model);
		link.setPictogramElement(diagram);

		diagramResource.getContents().add(diagram);

		// save
		try {
			modelResource.save(Collections.emptyMap());
			diagramResource.save(Collections.emptyMap());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
	}
}
