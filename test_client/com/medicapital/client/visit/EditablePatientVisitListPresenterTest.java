package com.medicapital.client.visit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import com.medicapital.client.dao.ServiceResponse;
import com.medicapital.client.evaluation.CreateVisitEvaluationPresenter;
import com.medicapital.client.evaluation.VisitEvaluationPresenter;
import com.medicapital.client.test.TestEventBus;
import com.medicapital.client.visit.EditVisitPresenter;
import com.medicapital.client.visit.EditablePatientVisitListPresenter;
import com.medicapital.client.visit.EditablePatientVisitListView;
import com.medicapital.client.visit.VisitPresenter;
import com.medicapital.common.commands.entity.CreateCommandResp;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.commands.entity.UpdateCommandResp;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateManager;
import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.Localization;
import com.medicapital.common.entities.PatientEvaluation;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.common.entities.User;

public class EditablePatientVisitListPresenterTest {

	private final DateManager dateManager = DateFactory.createDateManager();

	@Test
	public void testDisplayData() {
		EditablePatientVisitListView view = mock(EditablePatientVisitListView.class);
		EditablePatientVisitListPresenter visitListPresenter = new EditablePatientVisitListPresenter(view, new TestEventBus());
		Collection<PatientVisit> visits = new ArrayList<PatientVisit>();
		PatientVisit oldVisit = new PatientVisit();
		oldVisit.setId(1);
		Date oldVisitDate = new Date();
		dateManager.setDate(oldVisitDate, 2000, 1, 1);
		oldVisit.setStartTime(oldVisitDate);
		oldVisit.setLocalization(new Localization());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		oldVisit.setDoctor(doctor);
		visits.add(oldVisit);

		PatientVisit plannedVisit = new PatientVisit();
		plannedVisit.setId(2);
		Date plannedVisitDate = new Date();
		dateManager.setDate(plannedVisitDate, 2030, 1, 1);
		plannedVisit.setStartTime(plannedVisitDate);
		plannedVisit.setLocalization(new Localization());
		plannedVisit.setDoctor(doctor);
		visits.add(plannedVisit);

		visitListPresenter.display(visits);
		verify(view).addVisit(1, oldVisit.getStartTime(), null, "null null", false);
		verify(view).addPlannedVisit(2, plannedVisit.getStartTime(), null, "null null");
	}

	@Test
	public void testShowEditableVisit() {
		EditablePatientVisitListView view = mock(EditablePatientVisitListView.class);
		EditablePatientVisitListPresenter visitListPresenter = new EditablePatientVisitListPresenter(view, new TestEventBus());
		Collection<PatientVisit> visits = new ArrayList<PatientVisit>();
		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		Date visitDate = new Date();
		dateManager.setDate(visitDate, 2030, 1, 1);
		visit.setStartTime(visitDate);
		visit.setLocalization(new Localization());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visits.add(visit);

		EditVisitPresenter editVisitPresenter = mock(EditVisitPresenter.class);
		visitListPresenter.setEditVisitPresenter(editVisitPresenter);
		visitListPresenter.display(visits);
		visitListPresenter.displayVisitDetails(visit.getId());
		verify(editVisitPresenter).display(visit);
	}

	@Test
	public void testShowEditableVisitNoPresenter() {
		Collection<PatientVisit> visits = new ArrayList<PatientVisit>();
		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		Date visitDate = new Date();
		dateManager.setDate(visitDate, 2030, 1, 1);
		visit.setStartTime(visitDate);
		visit.setLocalization(new Localization());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visits.add(visit);

		EditablePatientVisitListView view = mock(EditablePatientVisitListView.class);
		EditablePatientVisitListPresenter visitListPresenter = new EditablePatientVisitListPresenter(view, new TestEventBus());
		visitListPresenter.display(visits);
		try {
			visitListPresenter.displayVisitDetails(visit.getId());
			fail("Edit visit presenter is not set");
		} catch (IllegalArgumentException e) {
			assertEquals("Edit visit presenter not set", e.getMessage());
		}
	}

	@Test
	public void testShowVisit() {
		Collection<PatientVisit> visits = new ArrayList<PatientVisit>();
		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		Date visitDate = new Date();
		dateManager.setDate(visitDate, 2000, 1, 1);
		visit.setStartTime(visitDate);
		visit.setLocalization(new Localization());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visits.add(visit);

		VisitPresenter visitPresenter = mock(VisitPresenter.class);
		EditablePatientVisitListView view = mock(EditablePatientVisitListView.class);
		EditablePatientVisitListPresenter visitListPresenter = new EditablePatientVisitListPresenter(view, new TestEventBus());
		visitListPresenter.setVisitPresenter(visitPresenter);
		visitListPresenter.display(visits);
		visitListPresenter.displayVisitDetails(visit.getId());
		verify(visitPresenter).display(visit);
	}

	@Test
	public void testShowVisitNoPresenter() {
		Collection<PatientVisit> visits = new ArrayList<PatientVisit>();
		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		Date visitDate = new Date();
		dateManager.setDate(visitDate, 2000, 1, 1);
		visit.setStartTime(visitDate);
		visit.setLocalization(new Localization());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visits.add(visit);

		EditablePatientVisitListView view = mock(EditablePatientVisitListView.class);
		EditablePatientVisitListPresenter visitListPresenter = new EditablePatientVisitListPresenter(view, new TestEventBus());
		visitListPresenter.display(visits);
		try {
			visitListPresenter.displayVisitDetails(visit.getId());
			fail("Visit presenter is not set");
		} catch (IllegalArgumentException e) {
			assertEquals("Visit presenter not set", e.getMessage());
		}
	}

	@Test
	public void testShowEvaluation() {
		Collection<PatientVisit> visits = new ArrayList<PatientVisit>();
		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		Date visitDate = new Date();
		dateManager.setDate(visitDate, 2000, 1, 1);
		visit.setStartTime(visitDate);
		visit.setLocalization(new Localization());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);

		PatientEvaluation evaluation = new PatientEvaluation();
		evaluation.setId(5);
		visit.setEvaluation(evaluation);
		visits.add(visit);

		EditablePatientVisitListView view = mock(EditablePatientVisitListView.class);
		EditablePatientVisitListPresenter visitListPresenter = new EditablePatientVisitListPresenter(view, new TestEventBus());
		visitListPresenter.display(visits);
		VisitEvaluationPresenter evaluationPresenter = mock(VisitEvaluationPresenter.class);
		visitListPresenter.setEvaluationPresenter(evaluationPresenter);
		visitListPresenter.displayVisitEvaluation(visit.getId());
		verify(evaluationPresenter).display(evaluation);
	}

	@Test
	public void testShowEvaluationNoPresenter() {
		Collection<PatientVisit> visits = new ArrayList<PatientVisit>();
		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		Date visitDate = new Date();
		dateManager.setDate(visitDate, 2000, 1, 1);
		visit.setStartTime(visitDate);
		visit.setLocalization(new Localization());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);

		PatientEvaluation evaluation = new PatientEvaluation();
		evaluation.setId(5);
		visit.setEvaluation(evaluation);
		visits.add(visit);

		EditablePatientVisitListView view = mock(EditablePatientVisitListView.class);
		EditablePatientVisitListPresenter visitListPresenter = new EditablePatientVisitListPresenter(view, new TestEventBus());
		visitListPresenter.display(visits);
		try {
			visitListPresenter.displayVisitEvaluation(visit.getId());
			fail("Evaluation presenter not set");
		} catch (IllegalArgumentException e) {
			assertEquals("Evaluation presenter not set", e.getMessage());
		}
	}

	@Test
	public void testCreateEvaluation() {
		Collection<PatientVisit> visits = new ArrayList<PatientVisit>();
		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		Date visitDate = new Date();
		dateManager.setDate(visitDate, 2000, 1, 1);
		visit.setStartTime(visitDate);
		visit.setLocalization(new Localization());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visits.add(visit);

		EditablePatientVisitListView view = mock(EditablePatientVisitListView.class);
		EditablePatientVisitListPresenter visitListPresenter = new EditablePatientVisitListPresenter(view, new TestEventBus());
		visitListPresenter.display(visits);
		CreateVisitEvaluationPresenter createEvaluationPresenter = mock(CreateVisitEvaluationPresenter.class);
		visitListPresenter.setCreateEvaluationPresenter(createEvaluationPresenter);
		visitListPresenter.addVisitEvaluation(visit.getId());
		verify(createEvaluationPresenter).display(any(PatientEvaluation.class));
	}

	@Test
	public void testCreateEvaluationNotAllowed() {
		Collection<PatientVisit> visits = new ArrayList<PatientVisit>();
		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		Date visitDate = new Date();
		dateManager.setDate(visitDate, 2000, 1, 1);
		visit.setStartTime(visitDate);
		visit.setLocalization(new Localization());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visits.add(visit);

		EditablePatientVisitListView view = mock(EditablePatientVisitListView.class);
		EditablePatientVisitListPresenter visitListPresenter = new EditablePatientVisitListPresenter(view, new TestEventBus());
		visitListPresenter.display(visits);
		CreateVisitEvaluationPresenter createEvaluationPresenter = mock(CreateVisitEvaluationPresenter.class);
		visitListPresenter.setCreateEvaluationPresenter(createEvaluationPresenter);
		visitListPresenter.setCanAddEvaluations(false);
		try {
			visitListPresenter.addVisitEvaluation(visit.getId());
		} catch (IllegalArgumentException e) {
			assertEquals("Evaluation can't be added to the visit list presenter", e.getMessage());
		}
	}

	@Test
	public void testCreateEvaluationNoPresenter() {
		Collection<PatientVisit> visits = new ArrayList<PatientVisit>();
		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		Date visitDate = new Date();
		dateManager.setDate(visitDate, 2000, 1, 1);
		visit.setStartTime(visitDate);
		visit.setLocalization(new Localization());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visits.add(visit);

		EditablePatientVisitListView view = mock(EditablePatientVisitListView.class);
		EditablePatientVisitListPresenter visitListPresenter = new EditablePatientVisitListPresenter(view, new TestEventBus());
		visitListPresenter.display(visits);
		try {
			visitListPresenter.addVisitEvaluation(visit.getId());
			fail("Create evaluation presenter not set");
		} catch (IllegalArgumentException e) {
			assertEquals("Create evaluation presenter not set", e.getMessage());
		}
	}

	@Test
	public void testUpdateEvaluationEvent() {
		Collection<PatientVisit> visits = new ArrayList<PatientVisit>();
		PatientVisit visit = spy(new PatientVisit());
		visit.setId(1);
		Date visitDate = new Date();
		dateManager.setDate(visitDate, 2000, 1, 1);
		visit.setStartTime(visitDate);
		visit.setLocalization(new Localization());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);

		PatientEvaluation evaluation = new PatientEvaluation();
		evaluation.setId(5);
		visit.setEvaluation(evaluation);
		visits.add(visit);

		EditablePatientVisitListView view = mock(EditablePatientVisitListView.class);
		TestEventBus eventBus = new TestEventBus();
		EditablePatientVisitListPresenter visitListPresenter = new EditablePatientVisitListPresenter(view, eventBus);
		visitListPresenter.display(visits);
		UpdateCommandResp<PatientEvaluation> command = new UpdateCommandResp<PatientEvaluation>(PatientEvaluation.class, 1);
		command.setUpdatedEntity(evaluation);
		eventBus.fireEvent(new ServiceResponse<PatientEvaluation>(command, command));
		verify(visit, times(2)).setEvaluation(evaluation);

	}

	@Test
	public void testCreateEvaluationEvent() {
		Collection<PatientVisit> visits = new ArrayList<PatientVisit>();
		PatientVisit visit = spy(new PatientVisit());
		visit.setId(1);
		Date visitDate = new Date();
		dateManager.setDate(visitDate, 2000, 1, 1);
		visit.setStartTime(visitDate);
		visit.setLocalization(new Localization());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visits.add(visit);

		PatientEvaluation evaluation = new PatientEvaluation();
		evaluation.setVisitId(visit.getId());
		evaluation.setId(5);

		EditablePatientVisitListView view = mock(EditablePatientVisitListView.class);
		TestEventBus eventBus = new TestEventBus();
		EditablePatientVisitListPresenter visitListPresenter = new EditablePatientVisitListPresenter(view, eventBus);
		visitListPresenter.display(visits);
		CreateCommandResp<PatientEvaluation> command = new CreateCommandResp<PatientEvaluation>(PatientEvaluation.class, evaluation.getId());
		command.setCreatedEntity(evaluation);
		eventBus.fireEvent(new ServiceResponse<PatientEvaluation>(command, command));
		verify(visit).setEvaluation(evaluation);
	}

	@Test
	public void testDeleteEvaluationEvent() {
		Collection<PatientVisit> visits = new ArrayList<PatientVisit>();
		PatientVisit visit = spy(new PatientVisit());
		visit.setId(1);
		Date visitDate = new Date();
		dateManager.setDate(visitDate, 2000, 1, 1);
		visit.setStartTime(visitDate);
		visit.setLocalization(new Localization());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visits.add(visit);

		PatientEvaluation evaluation = new PatientEvaluation();
		evaluation.setVisitId(visit.getId());
		evaluation.setId(5);
		visit.setEvaluation(evaluation);

		EditablePatientVisitListView view = mock(EditablePatientVisitListView.class);
		TestEventBus eventBus = new TestEventBus();
		EditablePatientVisitListPresenter visitListPresenter = new EditablePatientVisitListPresenter(view, eventBus);
		visitListPresenter.display(visits);

		DeleteCommandResp<PatientEvaluation> command = new DeleteCommandResp<PatientEvaluation>(PatientEvaluation.class);
		Set<Integer> deletedEvaluations = new HashSet<Integer>();
		deletedEvaluations.add(evaluation.getId());
		command.setDeletedElements(deletedEvaluations);

		eventBus.fireEvent(new ServiceResponse<PatientEvaluation>(command, command));
		verify(visit).setEvaluation(null);
	}

}
