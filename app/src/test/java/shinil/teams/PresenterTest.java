package shinil.teams;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import shinil.teams.api.TeamApiService;
import shinil.teams.presenter.TeamPresenterImp;
import shinil.teams.view.TeamView;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author shinilms
 */

public class PresenterTest {
    @Mock
    private TeamApiService charactersDataSource;
    @Mock
    private TeamView view;

    @BeforeClass
    public static void setUpClass() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };
        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_load_data_to_list() {
        TeamPresenterImp mainPresenter = new TeamPresenterImp(this.charactersDataSource, this.view);

        when(charactersDataSource.getTeams()).thenReturn(Observable.just(anyList()));

        mainPresenter.getTeams();

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).onShowProgress(true);
        inOrder.verify(view, times(1)).onTeamLoaded(anyList());
        inOrder.verify(view, times(1)).onShowProgress(false);
        verify(view, never()).onLoadFailed(anyString());
    }

    @Test
    public void test_return_error_to_ui() {
        TeamPresenterImp mainPresenter = new TeamPresenterImp(this.charactersDataSource, this.view);

        Exception exception = new Exception();

        when(charactersDataSource.getTeams()).thenReturn(Observable.error(exception));

        mainPresenter.getTeams();

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).onShowProgress(true);
        inOrder.verify(view, times(1)).onLoadFailed(anyString());
        inOrder.verify(view, times(1)).onShowProgress(false);
        verify(view, never()).onTeamLoaded(anyList());
    }

    @After
    public void tearDown() throws Exception {
        reset(charactersDataSource);
        reset(view);
    }

    @AfterClass
    public static void tearDownClass() {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }
}
