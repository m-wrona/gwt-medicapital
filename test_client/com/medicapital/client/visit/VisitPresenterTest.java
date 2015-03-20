package com.medicapital.client.visit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import com.medicapital.client.dao.ServiceResponse;
import com.medicapital.client.evaluation.CreateVisitEvaluationPresenter;
import com.medicapital.client.evaluation.VisitEvaluationPresenter;
import com.medicapital.client.test.TestEventBus;
import com.medicapital.client.visit.VisitPresenter;
import com.medicapital.client.visit.VisitPresenterView;
import com.medicapital.common.commands.entity.CreateCommandResp;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.commands.entity.UpdateCommandResp;
import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.Localization;
import com.medicapital.common.entities.PatientEvaluation;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.common.entities.User;

public class VisitPresenterTest {

	@Test
	public void testDisplayVisitNoEvaluation() {
		VisitPresenterView view = mock(VisitPresenterView.class);
		VisitPresenter visitPresenter = new VisitPresenter(view, new TestEventBus());

		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setPatient(new User());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visit.setLocalization(new Localization());
		visitPresenter.display(visit);
		verify(view).setVisitTitle(null);
		verify(view).setCreateEvaluationVisible(true);
		verify(view).setViewVisible(true);
	}

	@Test
	public void testDisplayVisitWithEvaluation() {
		VisitPresenterView view = mock(VisitPresenterView.class);
		VisitPresenter visitPresenter = new VisitPresenter(view, new TestEventBus());

		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setPatient(new User());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visit.setLocalization(new Localization());
		visit.setEvaluation(mock(PatientEvaluation.class));
		visitPresenter.display(visit);
		verify(view).setVisitTitle(null);
		verify(view).setDisplayEvaluationVisible(true);
		verify(view).setViewVisible(true);
	}

	@Test
	public void testDisplayEvaluation() {
		VisitPresenterView view = mock(VisitPresenterView.class);
		VisitPresenter visitPresenter = new VisitPresenter(view, new TestEventBus());

		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setPatient(new User());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visit.setLocalization(new Localization());
		PatientEvaluation evaluation = new PatientEvaluation();
		evaluation.setId(5);
		visit.setEvaluation(evaluation);

		visitPresenter.display(visit);

		VisitEvaluationPresenter evaluationPresenter = mock(VisitEvaluationPresenter.class);
		visitPresenter.setEvaluationPresenter(evaluationPresenter);
		visitPresenter.displayEvaluation();
		verify(evaluationPresenter).display(evaluation);
	}

	@Test
	public void testDisplayEvaluationNoVisitDisplayed() {
		VisitPresenterView view = mock(VisitPresenterView.class);
		VisitPresenter visitPresenter = new VisitPresenter(view, new TestEventBus());

		try {
			visitPresenter.displayEvaluation();
			fail("No visit is displayed");
		} catch (IllegalArgumentException ex) {
			assertEquals("No visit is displayed", ex.getMessage());
		}
	}

	@Test
	public void testDisplayEvaluationNoEvaluationPresenter() {
		VisitPresenterView view = mock(VisitPresenterView.class);
		VisitPresenter visitPresenter = new VisitPresenter(view, new TestEventBus());

		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setPatient(new User());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visit.setLocalization(new Localization());
		PatientEvaluation evaluation = new PatientEvaluation();
		evaluation.setId(5);
		visit.setEvaluation(evaluation);

		visitPresenter.display(visit);
		try {
			visitPresenter.displayEvaluation();
			fail("Evaluation presenter not set");
		} catch (IllegalArgumentException ex) {
			assertEquals("Evaluation presenter not set", ex.getMessage());
		}
	}

	@Test
	public void testDisplayNullEvaluation() {
		VisitPresenterView view = mock(VisitPresenterView.class);
		VisitPresenter visitPresenter = new VisitPresenter(view, new TestEventBus());

		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setPatient(new User());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visit.setLocalization(new Localization());

		visitPresenter.display(visit);

		VisitEvaluationPresenter evaluationPresenter = mock(VisitEvaluationPresenter.class);
		visitPresenter.setEvaluationPresenter(evaluationPresenter);
		try {
			visitPresenter.displayEvaluation();
			fail("Current visit has no evaluation assigned");
		} catch (IllegalArgumentException ex) {
			assertEquals("Current visit has no evaluation to display", ex.getMessage());
		}
	}

	@Test
	public void testCreateEvaluation() {
		VisitPresenterView view = mock(VisitPresenterView.class);
		VisitPresenter visitPresenter = new VisitPresenter(view, new TestEventBus());

		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setPatient(new User());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visit.setLocalization(new Localization());

		visitPresenter.display(visit);

		CreateVisitEvaluationPresenter evaluationPresenter = mock(CreateVisitEvaluationPresenter.class);
		visitPresenter.setCreateEvaluationPresenter(evaluationPresenter);
		visitPresenter.createEvaluation();
		verify(evaluationPresenter).display(any(PatientEvaluation.class));
	}

	@Test
	public void testCreateEvaluationNotAllowed() {
		VisitPresenterView view = mock(VisitPresenterView.class);
		VisitPresenter visitPresenter = new VisitPresenter(view, new TestEventBus());

		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setPatient(new User());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visit.setLocalization(new Localization());

		visitPresenter.display(visit);

		CreateVisitEvaluationPresenter evaluationPresenter = mock(CreateVisitEvaluationPresenter.class);
		visitPresenter.setCreateEvaluationPresenter(evaluationPresenter);
		visitPresenter.createEvaluation();
		verify(evaluationPresenter).display(any(PatientEvaluation.class));
		visitPresenter.setCanAddEvaluations(false);
		try {
			visitPresenter.createEvaluation();
		} catch (IllegalArgumentException e) {
			assertEquals("Evaluation can't be added using presenter", e.getMessage());
		}
	}

	@Test
	public void testCreateEvaluationNoVisitDisplayed() {
		VisitPresenterView view = mock(VisitPresenterView.class);
		VisitPresenter visitPresenter = new VisitPresenter(view, new TestEventBus());

		try {
			visitPresenter.createEvaluation();
			fail("No visit is displayed");
		} catch (IllegalArgumentException ex) {
			assertEquals("No visit is displayed", ex.getMessage());
		}
	}

	@Test
	public void testCreateEvaluationNoEvaluationPresenter() {
		VisitPresenterView view = mock(VisitPresenterView.class);
		VisitPresenter visitPresenter = new VisitPresenter(view, new TestEventBus());

		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setPatient(new User());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visit.setLocalization(new Localization());

		visitPresenter.display(visit);
		try {
			visitPresenter.createEvaluation();
			fail("Create evaluation presenter not set");
		} catch (IllegalArgumentException ex) {
			assertEquals("Create evaluation presenter not set", ex.getMessage());
		}
	}

	@Test
	public void testHandleUpdateEvaluationEvent() {
		VisitPresenterView view = mock(VisitPresenterView.class);
		TestEventBus eventBus = new TestEventBus();
		VisitPresenter visitPresenter = new VisitPresenter(view, eventBus);

		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setPatient(new User());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visit.setLocalization(new Localization());
		PatientEvaluation evaluation = new PatientEvaluation();
		evaluation.setId(5);
		evaluation.setVisitId(visit.getId());
		visit.setEvaluation(evaluation);

		visitPresenter.display(visit);
		UpdateCommandResp<PatientEvaluation> command = new UpdateCommandResp<PatientEvaluation>(PatientEvaluation.class, 1);
		evaluation = new PatientEvaluation();
		evaluation.setId(5);
		command.setUpdatedEntity(evaluation);
		evaluation.setDescription("updated");
		evaluation.setVisitId(visit.getId());
		eventBus.fireEvent(new ServiceResponse<PatientEvaluation>(command, command));
		assertEquals("updated", visitPresenter.getCurrentEntity().getEvaluation().getDescription());

	}

	@Test
	public void testHandleCreateEvaluationEvent() {
		VisitPresenterView view = mock(VisitPresenterView.class);
		TestEventBus eventBus = new TestEventBus();
		VisitPresenter visitPresenter = new VisitPresenter(view, eventBus);

		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setPatient(new User());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visit.setLocalization(new Localization());
		visitPresenter.display(visit);

		PatientEvaluation evaluation = new PatientEvaluation();
		evaluation.setId(5);
		evaluation.setVisitId(visit.getId());
		CreateCommandResp<PatientEvaluation> command = new CreateCommandResp<PatientEvaluation>(PatientEvaluation.class, evaluation.getId());
		command.setCreatedEntity(evaluation);
		eventBus.fireEvent(new ServiceResponse<PatientEvaluation>(command, command));
		assertNotNull(visitPresenter.getCurrentEntity().getEvaluation());

	}

	@Test
	public void testHandleDeleteEvaluationEvent() {
		VisitPresenterView view = mock(VisitPresenterView.class);
		TestEventBus eventBus = new TestEventBus();
		VisitPresenter visitPresenter = new VisitPresenter(view, eventBus);

		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setPatient(new User());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visit.setLocalization(new Localization());
		PatientEvaluation evaluation = new PatientEvaluation();
		evaluation.setId(5);
		evaluation.setVisitId(visit.getId());
		visit.setEvaluation(evaluation);

		visitPresenter.display(visit);
		DeleteCommandResp<PatientEvaluation> command = new DeleteCommandResp<PatientEvaluation>(PatientEvaluation.class);
		Set<Integer> deletedEvaluations = new HashSet<Integer>();
		deletedEvaluations.add(evaluation.getId());
		command.setDeletedElements(deletedEvaluations);
		eventBus.fireEvent(new ServiceResponse<PatientEvaluation>(command, command));
		assertNull(visitPresenter.getCurrentEntity().getEvaluation());
	}

}
